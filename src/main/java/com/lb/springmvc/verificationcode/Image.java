package com.lb.springmvc.verificationcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.lb.springmvc.util.FileUtils;

public class Image {

	private static final String FILEBASEURL ="E:\\workspace\\springmvc\\src\\main\\webapp\\verificationCodeImage\\";
	
	private static Map<String,ImageGroup> imageGroupMap = new HashMap<String,ImageGroup>();
	private static Map<Integer,Map<String,ImageGroup>> countGroupsMap = new HashMap<Integer, Map<String,ImageGroup>>();
	
	private static BufferedImage getBufferedImage(String fileUrl) throws IOException{
		File f = new File(FILEBASEURL + fileUrl);
		return ImageIO.read(f);
	}
	
	public static boolean saveImage(BufferedImage saveImg,String fileUrl,String format){
		File file = new File(fileUrl);
		try {
			return ImageIO.write(saveImg, format, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static ImageResult generateImage() throws IOException {
		initImageGroup();
		GenerateImageGroup generateImageGroup=randomImageGroups();
		List<BufferedImageWrap> images=new ArrayList<BufferedImageWrap>();
		for(ImageGroup group:generateImageGroup.getGroups()){
			for(String imgName:group.getImages()){
				images.add(new BufferedImageWrap(false,getBufferedImage(imgName)));
			}
		}
		
		for(String imgName:generateImageGroup.getKeyGroup().getImages()){
			images.add(new BufferedImageWrap(true,getBufferedImage(imgName)));
		}
		return mergeImage(images,generateImageGroup.getKeyGroup().getName());
	}

	private static ImageResult mergeImage(List<BufferedImageWrap> imageWraps, String tip) {
		Collections.shuffle(imageWraps);
		//原始图片宽200像素，高200像素
		int width = 200;
		int height = 200;
		int totalWidth = width * 4;
		
		BufferedImage destImage = new BufferedImage(totalWidth, 400, BufferedImage.TYPE_INT_RGB);
		int x1 = 0;
		int x2 = 0;
		int order = 0;
		StringBuilder keysOrder = new StringBuilder();
		Set<Integer> keySet = new HashSet<Integer>();
		for(BufferedImageWrap image:imageWraps){
			int[] rgb = image.getBufferedImage().getRGB(0, 0, width, height, null, 0, width);
			if(image.isKey()){
				int x = (order % 4) * 200;
				int y = order < 4 ? 0 : 200;
				keySet.add(order);
				keysOrder.append(order).append("(").append(x).append(",").append(y).append(")|");
			}
			if(order < 4){
				destImage.setRGB(x1, 0, width,height,rgb,0,width);//设置上半部分的RGB
				x1 += width;
			} else {
				destImage.setRGB(x2, height, width,height,rgb,0,width);
				x2 += width;
			}
			order++;
		}
		keysOrder.deleteCharAt(keysOrder.length() - 1);
		//System.out.println("答案位置："+ keysOrder);
		String fileName = UUID.randomUUID().toString().replace("-","");
		String fileUrl = FileUtils.getSystemPath()+"\\targetImage\\"+ fileName+".jpg";
		saveImage(destImage, fileUrl,"jpg");
		
		ImageResult ir = new ImageResult();
		ir.setName(fileName + ".jpg");
		ir.setKeySet(keySet);
		ir.setUniqueKey(fileName);
		ir.setTip(tip);
		return ir;
	}

	private static GenerateImageGroup randomImageGroups() {
		List<ImageGroup> result = new ArrayList<ImageGroup>();
		int num = random(0,imageGroupMap.size()-1);
		
		String name = new ArrayList<String>(imageGroupMap.keySet()).get(num);
		ImageGroup keyGroup = imageGroupMap.get(name);
		
		Map<Integer,Map<String,ImageGroup>> thisCountGroupsMap = new HashMap<Integer, Map<String,ImageGroup>>(countGroupsMap);
		thisCountGroupsMap.get(keyGroup.getCount()).remove(name);
		
		//假设总量8个，每种名称图片只有2个或者4个，为了逻辑更简单些
		int leftCount = 8 - keyGroup.getCount();
		if(leftCount == 4){
			if(new Random().nextInt() % 2 == 0){
				List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroupsMap.get(4).values());
				if(groups.size() > 1){
					num = random(0,groups.size() - 1);
				} else {
					num = 0;
				}
				result.add(groups.get(num));
			} else {
				List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
				int num1 = random(0,groups.size() - 1);
				result.add(groups.get(num1));
				
				int num2 = random(0,groups.size() - 1,num1);
				result.add(groups.get(num2));
			}
		} else if(leftCount == 6){
			if(new Random().nextInt() % 2 == 0){
				List<ImageGroup> groups1 = new ArrayList<ImageGroup>(thisCountGroupsMap.get(4).values());
				int num1 = random(0,groups1.size() - 1);
				result.add(groups1.get(num1));
				
				List<ImageGroup> groups2 = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
				int num2 = random(0,groups2.size() - 1);
				result.add(groups2.get(num2));
			} else {
				List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroupsMap.get(2).values());
				int num1 = random(0,groups.size() - 1);
				result.add(groups.get(num1));
				
				int num2 = random(0,groups.size() - 1,num1);
				result.add(groups.get(num2));
				
				int num3 = random(0,groups.size() - 1,num1,num2);
				result.add(groups.get(num3));
			}
		}
		return new GenerateImageGroup(keyGroup, result);
	}

	private static void initImageGroup() {
		ImageGroup group1 = new ImageGroup("包包",4,"bao/1.jpg","bao/2.jpg","bao/3.jpg","bao/4.jpg");
		ImageGroup group2 = new ImageGroup("老虎",4,"laohu/1.jpg","laohu/2.jpg","laohu/3.jpg","laohu/4.jpg");
		ImageGroup group3 = new ImageGroup("糖葫芦",4,"tanghulu/1.jpg","tanghulu/2.jpg","tanghulu/3.jpg","tanghulu/4.jpg");
		ImageGroup group4 = new ImageGroup("小幕",4,"xiaomu/1.jpg","xiaomu/2.jpg","xiaomu/3.jpg","xiaomu/4.jpg");
		ImageGroup group5 = new ImageGroup("柚子",4,"youzi/1.jpg","youzi/2.jpg","youzi/3.jpg","youzi/4.jpg");
		ImageGroup group6 = new ImageGroup("订书机",2,"dingshuji/1.jpg","dingshuji/2.jpg");
		ImageGroup group7 = new ImageGroup("蘑菇",2,"mogu/1.jpg","mogu/2.jpg");
		ImageGroup group8 = new ImageGroup("磁铁",2,"citie/1.jpg","citie/2.jpg");
		ImageGroup group9 = new ImageGroup("土豆",2,"tudou/1.jpg","tudou/2.jpg");
		ImageGroup group10 = new ImageGroup("兔子",2,"tuzi/1.jpg","tuzi/2.jpg");
		ImageGroup group11 = new ImageGroup("仙人球",2,"xianrenqiu/1.jpg","xianrenqiu/2.jpg");
		
		initMap(group1,group2,group3,group4,group5,group6,group7,group8,group9,group10,group11);
	}

	private static void initMap(ImageGroup... groups) {
		for(ImageGroup group:groups){
			imageGroupMap.put(group.getName(),group);
			if(!countGroupsMap.containsKey(group.getCount())){
				countGroupsMap.put(group.getCount(),new HashMap<String,ImageGroup>());
			}
			countGroupsMap.get(group.getCount()).put(group.getName(),group);
		}
	}
	
	private static int random(int min,int max){
		Random random =new Random();
		return random.nextInt(max - min +1) + min;
	}
	
	private static int random(int min,int max,Integer... not){
		int num = random(min,max);
		List<Integer> notList = Arrays.asList(not);
		while(notList.contains(num)){
			num = random(min,max);
		}
		return num;
	}
}
