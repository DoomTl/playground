package com.example.demo.application;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

class SowExporterTest {

	@Test
	void test() throws Exception {

		String output = "output.docx";
		ProductDetail productDetail = new ProductDetail();
		productDetail.setTable1("123");
		productDetail.setTable2("456");
		productDetail.setDetails(listItems());
		new SowExporter().export(productDetail, FileUtils.openOutputStream(new File(output)));
	}

	private List<ProductDetail.Item> listItems() {

		List<ProductDetail.Item> collect = IntStream.range(1, 10).mapToObj(ProductDetail.Item::of).collect(toList());
		collect.stream().collect(groupingBy(ProductDetail.Item::getProp1))
				.forEach((key, value) -> value.stream().peek(e -> e.setMerge1Start(false)).findFirst()
						.ifPresent(e -> e.setMerge1Start(true)));
		collect.stream().collect(groupingBy(ProductDetail.Item::getProp2))
				.forEach((key, value) -> value.stream().peek(e -> e.setMerge2Start(false)).findFirst()
						.ifPresent(e -> e.setMerge2Start(true)));
		return collect;
	}
}