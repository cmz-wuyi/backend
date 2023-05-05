package com.sicnu.boot.test;

import com.alibaba.fastjson.JSON;
import com.sicnu.boot.dto.CollegeDTO;
import com.sicnu.boot.dto.QuestionDTO;
import com.sicnu.boot.dto.VideoDTO;
import com.sicnu.boot.mapper.CollegeMapper;
import com.sicnu.boot.mapper.QuestionMapper;
import com.sicnu.boot.mapper.VideoMapper;
import com.sicnu.boot.pojo.College;
import com.sicnu.boot.pojo.Question;
import com.sicnu.boot.pojo.Video;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author :  胡建华
 * Data:    2023/01/06 10:18
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EsTest {
    @Resource
    private RestHighLevelClient client;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private CollegeMapper collegeMapper;

    @Test
    void testCreateIndexQuestionByIK() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("questions");
        String json = "{\n" +
                "\t\"mappings\":{\n" +
                "    \t\"questions\":{\n" +
                "            \"properties\":{\n" +
                "      \t\t\"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "      \t\t\"questionId\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "      \t\t\"questionTitle\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\",\n" +
                "         \t\t\"copy_to\":\"all\"\n" +
                "      \t\t},\n" +
                "      \t\t\"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\"\n" +
                "      \t\t}\n" +
                "        }\n" +
                "        }\n" +
                "\t}\n" +
                "}";
        //设置请求中的参数
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testCreateIndexVideoByIK()throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("videos");
        String json = "{\n" +
                "\t\"mappings\":{\n" +
                "    \t\"videos\":{\n" +
                "            \"properties\":{\n" +
                "      \t\t\"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "      \t\t\"videoId\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "            \"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\",\n" +
                "         \t\t\"copy_to\":\"all\"\n" +
                "      \t\t},\n" +
                "      \t\t\"introduction\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\",\n" +
                "         \t\t\"copy_to\":\"all\"\n" +
                "      \t\t},\n" +
                "      \t\t\"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\"\n" +
                "      \t\t}\n" +
                "        }\n" +
                "        }\n" +
                "\t}\n" +
                "}";
        //设置请求中的参数
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testCreateIndexCollegeByIK()throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("colleges");
        String json = "{\n" +
                "\t\"mappings\":{\n" +
                "    \t\"colleges\":{\n" +
                "            \"properties\":{\n" +
                "      \t\t\"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "      \t\t\"collegeId\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "      \t\t},\n" +
                "      \t\t\"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\",\n" +
                "         \t\t\"copy_to\":\"all\"\n" +
                "      \t\t},\n" +
                "            \"introduction\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\",\n" +
                "         \t\t\"copy_to\":\"all\"\n" +
                "      \t\t},\n" +
                "      \t\t\"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "         \t\t\"analyzer\":\"ik_max_word\"\n" +
                "      \t\t}\n" +
                "        }\n" +
                "        }\n" +
                "\t}\n" +
                "}";
        //设置请求中的参数
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testCreateQuestionAll() throws IOException {
        List<Question> questionList = questionMapper.getQuestionList(null);
        BulkRequest bulk = new BulkRequest();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO(question.getQuestionId(),question.getQuestionTitle());
            IndexRequest request = new IndexRequest("questions").id(questionDTO.getQuestionId().toString());
            String json = JSON.toJSONString(questionDTO);
            request.source(json,XContentType.JSON);
            bulk.add(request);
        }
        client.bulk(bulk,RequestOptions.DEFAULT);
    }

    @Test
    void testCreateVideoAll() throws IOException {
        List<Video> videoListBySelective = videoMapper.getVideoListBySelective(null);
        BulkRequest bulk = new BulkRequest();
        for (Video video : videoListBySelective) {
            VideoDTO videoDTO = new VideoDTO(video.getVideoId(), video.getName(), video.getIntroduction());
            IndexRequest request = new IndexRequest("videos").id(videoDTO.getVideoId().toString());
            String json = JSON.toJSONString(videoDTO);
            request.source(json,XContentType.JSON);
            bulk.add(request);
        }
        client.bulk(bulk,RequestOptions.DEFAULT);
    }

    @Test
    void testCreateCollegeAll() throws IOException {
        List<College> allCollege = collegeMapper.getAllCollege();
        BulkRequest bulk = new BulkRequest();
        for (College college : allCollege) {
            CollegeDTO collegeDTO = new CollegeDTO(college.getCollegeId(),college.getName(),college.getIntroduction());
            IndexRequest request = new IndexRequest("colleges").id(collegeDTO.getCollegeId().toString());
            String json = JSON.toJSONString(collegeDTO);
            request.source(json,XContentType.JSON);
            bulk.add(request);
        }
        client.bulk(bulk,RequestOptions.DEFAULT);
    }

    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("videos");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //模糊匹配
        searchSourceBuilder.query(QueryBuilders
                .fuzzyQuery("name", "数学")
                .fuzziness(Fuzziness.AUTO));
        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name")
                .field("introduction")//若有关键字切可以分词，则可以高亮，写*可以匹配所有字段
                        .requireFieldMatch(false)//因为高亮查询默认是对查询字段即description就行高亮，可以关闭字段匹配，
                // 这样就可以对查询到的多个字段（前提是有关键词并且改字段可以分词）进行高亮显示
                .preTags("<span style='color:red'>")//手动前缀标签
                .postTags("</span>");

        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            VideoDTO videoDTO = JSON.parseObject(source, VideoDTO.class);
            //获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            //如果高亮字段包含在introduction中
            if (highlightFields.containsKey("introduction")){
                Text[] fragments = highlightFields.get("introduction").getFragments();
                for (Text fragment : fragments) {
                    videoDTO.setIntroduction(fragment.toString());
                }
            }
            //如果高亮字段包含在name中
            if (highlightFields.containsKey("name")){
                Text[] fragments = highlightFields.get("name").getFragments();
                for (Text fragment : fragments) {
                    videoDTO.setName(fragment.toString());
                }
            }
        }
    }
}
