package com.weiwei.anji.dao.impl;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.weiwei.anji.dao.ITokenDAO;
import com.weiwei.anji.dbmodel.TableCustomers;
import com.weiwei.anji.dbmodel.TableToken;

public class TokenDAOImpl implements ITokenDAO {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	public static final long expireTime = 30 * 24 * 60 * 60; //30 days
	
	// The following constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
	public static final int PBKDF2_ITERATIONS = 1000;
	
    protected JdbcTemplate jdbcTemplate;

	@Override
	public void storeToken(String token, String username) {
		logger.info("Calling Method: storeToken(String token, String username)");
		try{
			String[] hashsalt = createHash(token);
			String hash = hashsalt[0];
			String salt = hashsalt[1];
			Calendar calendar = Calendar.getInstance();
			Timestamp date = new Timestamp(calendar.getTimeInMillis());
			String sql = "UPDATE customers_token SET token=?, salt=?, lastVerifyTime=? WHERE userName=?";
			jdbcTemplate.update(sql, hash, salt, date, username);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean validateToken(String token, String username) {
		String sql = "SELECT * FROM customers_token WHERE userName=?";
		List<TableToken> result = jdbcTemplate.query(sql, new String[]{username}, new BeanPropertyRowMapper(TableToken.class));
		if(result == null || result.size() == 0)
			return false;
		else{
			try{
				if (validateToken(token, result.get(0).getToken(), result.get(0).getSalt()) ){
					Calendar calendar = Calendar.getInstance();
					Timestamp date = new Timestamp(calendar.getTimeInMillis());
					long diff = (date.getTime() - result.get(0).getLastVerifyTime().getTime())/1000;
					if(diff < expireTime){
						int id = result.get(0).getId();
						String sql_update_time = "UPDATE customers_token SET lastVerifyTime=? WHERE id=?";
						jdbcTemplate.update(sql_update_time, date, id);
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	@Override
	public void expireToken(String username) {
		logger.info("Calling Method: expireToken(String token, String username)");
		try{
			Timestamp date = Timestamp.valueOf("2012-10-01 00:00:00");
			String sql = "UPDATE customers_token SET lastVerifyTime=? WHERE userName=?";
			jdbcTemplate.update(sql, date, username);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean validateToken(String token, String correctHash, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
		return validateToken(token.toCharArray(), correctHash, salt);
	}
	
	public static boolean validateToken(char[] password, String correctHash, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] salt_byte = fromHex(salt);
        byte[] hash_byte = fromHex(correctHash);
        // Compute the hash of the provided password, using the same salt, 
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt_byte, PBKDF2_ITERATIONS, hash_byte.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash_byte, testHash);
    }
	
	public static String[] createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
		return createHash(password.toCharArray());
	}
	/**
     * Returns a salted PBKDF2 hash of the password.
     *
     * @param   password    the password to hash
     * @return              a salted PBKDF2 hash of the password
     */
    public static String[] createHash(char[] password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return new String[]{toHex(hash), toHex(salt)};
    }
    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line 
     * system using a timing attack and then attacked off-line.
     * 
     * @param   a       the first byte array
     * @param   b       the second byte array 
     * @return          true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
    /**
     *  Computes the PBKDF2 hash of a password.
     *
     * @param   password    the password to hash.
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBDKF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }
    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }
    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
