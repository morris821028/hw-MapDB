package com.morris;

import java.util.Scanner;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerCompressionWrapper;

public class MapDbLaunch {
	public static void main(String[] args) {
//		DB db = DBMaker.fileDB("morrisDiskDB").fileMmapEnable().fileMmapEnableIfSupported().fileMmapPreclearDisable()
//				.allocateIncrement(512 * 1024 * 1024).closeOnJvmShutdown().cleanerHackEnable().make();

//		DB db = DBMaker.memoryDB().make();

		DB db = DBMaker.heapDB().make();

		HTreeMap<Long, long[]> myMap = db.hashMap("data")
				.keySerializer(new SerializerCompressionWrapper<>(Serializer.LONG))
				.valueSerializer(new SerializerCompressionWrapper<>(Serializer.LONG_ARRAY)).createOrOpen();

		for (int i = 0, id = 0; i < 3000; i++) {
			for (int j = 0; j < 3000; j++) {
				myMap.put((long) id, new long[] { (long) (i * 1000000), (long) (j * 1000000) });
				id++;
			}
		}

		System.out.println("Insertion done.");

		Scanner cin = new Scanner(System.in);

		while (cin.hasNext()) {
			long q = cin.nextLong();
			long[] point = myMap.get(q);

			System.err.println("Point(" + point[0] / 1000000d + "," + point[1] / 1000000d + ")");
		}
 
		db.close();
	}
}
