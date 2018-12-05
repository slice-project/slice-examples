package org.etri.slice.integration.service;

public interface MusicGanreRecommender {
	
	String getRecommendedGanre(int gender, int age_low, int age_high, double happiness);
}
