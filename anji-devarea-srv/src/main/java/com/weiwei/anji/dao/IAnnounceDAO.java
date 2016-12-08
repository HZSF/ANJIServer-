package com.weiwei.anji.dao;

import java.util.List;

import com.weiwei.anji.dbmodel.Announce;
import com.weiwei.anji.dbmodel.AnnounceNew;

public interface IAnnounceDAO {
	public List<Announce> findBySequenceId(int startId, int endId);
	public List<?> findByUrl(String url);
	public List<Announce> findGovBySequenceId(int startId, int endId);
	public List<?> findGovByUrl(String url);
	public List<AnnounceNew> findBySequenceIdNew(int startId, int endId);
	public List<AnnounceNew> findGovBySequenceIdNew(int startId, int endId);
}
