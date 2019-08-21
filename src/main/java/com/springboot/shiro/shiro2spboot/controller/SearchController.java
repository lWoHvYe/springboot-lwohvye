package com.springboot.shiro.shiro2spboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.shiro.shiro2spboot.entity.SearchEntity;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/search")
@Scope("prototype")
public class SearchController {
    //solr服务地址
    private static String solrUrl = "http://127.0.0.1:8080/solr/solr_core";
    private static HttpSolrClient solrServer = new HttpSolrClient.Builder(solrUrl).build();


    /**
     * 根据关键字及类别搜索
     *
     * @param searchKey 关键字
     * @param curPage
     * @return
     */
    @RequestMapping("/solrSearch")
    @ResponseBody
//    solr搜索
    @SuppressWarnings("unchecked")
    public String solrSearch(String searchKey, String curPage) {
        JSONObject json = new JSONObject();
        try {
            int currentPage = 1;
//            未传curPage默认第一页
            if (!StringUtils.isEmpty(curPage))
                currentPage = Integer.parseInt(curPage);
            if (!StringUtils.isEmpty(searchKey)) {
                searchKey = formatWords(searchKey);
                SolrQuery params = new SolrQuery();
                params.setQuery("name:" + searchKey);
//                开启edismax方式进行自定义权重算法
                params.set("defType", "edismax");
//                对于top为true的，加上指定的评分
                params.set("bf", "if(top,10,0)");
//            frange函数设置查询范围
                params.addFilterQuery("{!frange l=1}query($q)");
                params.setFields("gid,name,rectype,geom");
                params.set("start", (currentPage - 1) * 10); //起始行
                params.set("rows", 10); //行
                QueryResponse query = solrServer.query(params);
                SolrDocumentList result = query.getResults();
                long numFound = result.getNumFound();
                List<SearchEntity> list = new ArrayList<>();
                for (SolrDocument entries : result) {
                    SearchEntity entity = new SearchEntity();
                    entity.setId((Integer) entries.get("gid"));
                    entity.setName((String) entries.get("name"));
                    entity.setType((String) entries.get("rectype"));
                    entity.setGeom((String) entries.get("geom"));
                    list.add(entity);
                }
//            将结果拼接为geoJson
                json.put("list", list);
                json.put("numFound", numFound);
                json.put("curPage", currentPage);
                json.put("totalPage", numFound / 10 + 1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toJSONString();
    }

    /**
     * 查询关键字格式化
     *
     * @param keyWord
     * @return
     * @throws UnsupportedEncodingException
     */
    public String formatWords(String keyWord) throws UnsupportedEncodingException {

//          将url中%替换为%25 防止解析报错
        keyWord = URLDecoder.decode(keyWord.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
//          去除输入项中的可见符号
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keyWord);
//          去除空字符串
        keyWord = m.replaceAll("").trim();
//          返回格式化字符串
        return keyWord;
    }

}
