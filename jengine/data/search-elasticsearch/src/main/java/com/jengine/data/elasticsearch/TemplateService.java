package com.jengine.data.elasticsearch;

import com.jengine.data.elasticsearch.mydataframe.FieldsVersionType;
import com.jengine.data.elasticsearch.mydataframe.RecordType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequestBuilder;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateResponse;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequestBuilder;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequestBuilder;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * content
 *
 * @author nouuid
 * @date 6/12/2017
 * @since 0.1.0
 */
public class TemplateService {
  private static final Log logger = LogFactory.getLog(TemplateService.class);

  private TransportClient client;

  public TemplateService(TransportClient client) {
    this.client = client;
  }

  public boolean create(String templateName, String templatePattern, String type, Map<String, RecordType> properties) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info("TEMPLATE_CREATE=templateName:" + templateName + ",templatePattern:" + templatePattern + ",type:" + type + ",mapping:" + properties);
    }
    boolean res = false;

    XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
        .startObject()
        .startObject(type)
        .startObject("properties");
    for (String key : properties.keySet()) {
      if (properties.get(key) != null) {
        xContentBuilder.startObject(key);
      }
      if (properties.get(key).getType() != null) {
        xContentBuilder.field("type", properties.get(key).getType().toValue());
      }
      if (properties.get(key).getAnalyzer() != null) {
        xContentBuilder.field("analyzer", properties.get(key).getAnalyzer().toValue());
      }
      if (properties.get(key).getFields() != null) {
        xContentBuilder.startObject("fields");
        Map<String, FieldsVersionType> fieldsVersionTypeMap = properties.get(key).getFields();
        for (String fieldsVersion : fieldsVersionTypeMap.keySet()) {
          xContentBuilder.startObject(fieldsVersion);
          if (fieldsVersionTypeMap.get(fieldsVersion).getType() != null) {
            xContentBuilder.field("type", fieldsVersionTypeMap.get(fieldsVersion).getType().toValue());
          }
          if (fieldsVersionTypeMap.get(fieldsVersion).getAnalyzer() != null) {
            xContentBuilder.field("analyzer", fieldsVersionTypeMap.get(fieldsVersion).getAnalyzer().toValue());
          }
          if (properties.get(key).getFielddata() != null) {
            xContentBuilder.field("fielddata", properties.get(key).getFielddata().toValue());
          }
          xContentBuilder.endObject();
        }
        xContentBuilder.endObject();
      }
      if (properties.get(key).getStore() != null) {
        xContentBuilder.field("store", properties.get(key).getStore().toValue());
      }
      if (properties.get(key).getFormat() != null) {
        xContentBuilder.field("format", properties.get(key).getFormat().toValue());
      }
      if (properties.get(key).getFielddata() != null) {
        xContentBuilder.field("fielddata", properties.get(key).getFielddata().toValue());
      }
      if (properties.get(key) != null) {
        xContentBuilder.endObject();
      }
    }
    xContentBuilder.endObject()
        .endObject()
        .endObject();

    Settings.Builder builder = Settings.builder()
        .put("index.number_of_shards", 4)
        .put("index.number_of_replicas", 0)
        .put("index.analysis.filter.my_word_delimiter.generate_word_parts", true)
        .put("index.analysis.filter.my_word_delimiter.preserve_original", true)
        .put("index.analysis.filter.my_word_delimiter.catenate_words", false)
        .put("index.analysis.filter.my_word_delimiter.generate_number_parts", true)
        .put("index.analysis.filter.my_word_delimiter.catenate_all", false)
        .put("index.analysis.filter.my_word_delimiter.split_on_case_change", false)
        .put("index.analysis.filter.my_word_delimiter.type", "word_delimiter")
        .put("index.analysis.filter.my_word_delimiter.catenate_numbers", false)
        .put("index.analysis.filter.pattern_replace_filer.pattern", "^[A-Za-z0-9]{1,2}$")
        .put("index.analysis.filter.pattern_replace_filer.type", "pattern_replace")
        .put("index.analysis.filter.pattern_replace_filer.replacement", " ")
        .put("index.analysis.filter.log_word_delimiter.generate_word_parts", true)
        .put("index.analysis.filter.log_word_delimiter.preserve_original", false)
        .put("index.analysis.filter.log_word_delimiter.catenate_words", false)
        .put("index.analysis.filter.log_word_delimiter.generate_number_parts", true)
        .put("index.analysis.filter.log_word_delimiter.catenate_all", false)
        .put("index.analysis.filter.log_word_delimiter.type", "word_delimiter")
        .put("index.analysis.filter.log_word_delimiter.catenate_numbers", false)
        .put("index.analysis.filter.stop_filer.type", "stop")
        .put("index.analysis.filter.stop_filer.stopwords", "_english_")
        .put("index.analysis.filter.my_pinyin.padding_char", " ")
        .put("index.analysis.filter.my_pinyin.type", "pinyin")
        .put("index.analysis.filter.my_pinyin.first_letter", "prefix")
        .putArray("index.analysis.analyzer.ik.filter", Arrays.asList("lowercase", "log_word_delimiter", "stop_filer"))
        .put("index.analysis.analyzer.ik.type", "custom")
        .put("index.analysis.analyzer.ik.tokenizer", "ik_max_word")
        .putArray("index.analysis.analyzer.ik.char_filter", Arrays.asList("html_strip"))
        .put("index.analysis.analyzer.ik.type", "custom")
        .put("index.analysis.analyzer.ik.tokenizer", "ik_max_word")
        .put("index.analysis.analyzer.pinyin.filter", "word_delimiter")
        .put("index.analysis.analyzer.pinyin.type", "custom")
        .put("index.analysis.analyzer.pinyin.tokenizer", "my_pinyin")
        .put("index.analysis.char_filter.my_pattern.pattern", "\\n")
        .put("index.analysis.char_filter.my_pattern.type", "pattern_replace")
        .put("index.analysis.char_filter.my_pattern.replacement", " ")
        .put("index.analysis.tokenizer.my_pinyin.padding_char", " ")
        .put("index.analysis.tokenizer.my_pinyin.type", "pinyin")
        .put("index.analysis.tokenizer.my_pinyin.first_letter", "none");

    PutIndexTemplateRequestBuilder putIndexTemplateRequestBuilder = client.admin().indices().preparePutTemplate(templateName)
        .setSettings(builder)
        .setTemplate(templatePattern)
        .addMapping(type, xContentBuilder);
    PutIndexTemplateResponse putIndexTemplateResponse = putIndexTemplateRequestBuilder.get();
    if (putIndexTemplateResponse != null && putIndexTemplateResponse.isAcknowledged()) {
      res = true;
    }
    return res;
  }

  public List<IndexTemplateMetaData> get(String templateName) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("TEMPLATE_GET=" + templateName);
    }
    GetIndexTemplatesRequestBuilder getIndexRequestBuilder = client.admin().indices().prepareGetTemplates(templateName);
    GetIndexTemplatesResponse getIndexTemplatesResponse = getIndexRequestBuilder.get();
    List<IndexTemplateMetaData> res = null;
    if (getIndexTemplatesResponse != null) {
      res = getIndexTemplatesResponse.getIndexTemplates();
    }
    return res;
  }

  public boolean exists(String templateName) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("TEMPLATE_EXISTS=" + templateName);
    }
    boolean res = false;
    List<IndexTemplateMetaData> templates = get(templateName);
    if (!templates.isEmpty() && templates.size() > 0) {
      res = true;
    }
    return res;
  }

  public boolean delete(String templateName) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info("TEMPLATE_DELETE=" + templateName);
    }
    DeleteIndexTemplateRequestBuilder deleteIndexTemplateRequestBuilder = client.admin().indices().prepareDeleteTemplate(templateName);
    DeleteIndexTemplateResponse deleteIndexTemplateResponse = deleteIndexTemplateRequestBuilder.get();
    boolean res = false;
    if (deleteIndexTemplateResponse != null && deleteIndexTemplateResponse.isAcknowledged()) {
      res = true;
    }
    return res;
  }
}
