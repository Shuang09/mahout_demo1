package com.sh;

import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CityBlockSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
public class UserCFRecommender {
    public  static  void main(String[] args)  throws Exception {
        // 创建数据模型，不包含用户评分
        DataModel dm =  new GenericBooleanPrefDataModel(
                GenericBooleanPrefDataModel
                        .toDataMap( new FileDataModel( new File(
                                "F:\\Learning_kit\\项目\\Mahout\\data.txt"))));
        // 使用曼哈顿距离计算相似度
        UserSimilarity us =  new CityBlockSimilarity(dm);

        //指定NearestNUserNeighborhood作为近邻算法
        UserNeighborhood unb =  new NearestNUserNeighborhood(10, us, dm);

        // 构建不包含用户评分的UserCF推荐器
        Recommender re =  new GenericBooleanPrefUserBasedRecommender(dm, unb, us);

        // 输出推荐结果，为1号用户推荐2个商品
        List<RecommendedItem> list = re.recommend(1, 2);
        for (RecommendedItem recommendedItem : list) {
            System. out.println(recommendedItem.getItemID()+" : "+recommendedItem.getValue());
        }
    }
}