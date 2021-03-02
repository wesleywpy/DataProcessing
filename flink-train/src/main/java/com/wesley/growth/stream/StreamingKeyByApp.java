package com.wesley.growth.stream;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * StreamingKeyByApp
 * 定义Key的构建方式
 * @author WangPanYong
 * @since 2021/03/02 17:32
 */
public class StreamingKeyByApp {

    public static void main(String[] args) throws Exception {
        // step1 ：获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // step2：读取数据
        DataStreamSource<String> text = env.socketTextStream("127.0.0.1", 9999);

        // step3: transform
        text.flatMap(new RichFlatMapFunction<String, WC>() {

            /**
             * @param value 输入源数据
             * @param out 输出
             */
            @Override
            public void flatMap(String value, Collector<WC> out) throws Exception {
                String[] tokens = value.toLowerCase().split(",");
                for(String token : tokens) {
                    if(token.length() > 0) {
                        out.collect(new WC(token.trim(), 1));
                    }
                }
            }
        }).keyBy(new KeySelector<WC, String>() {
            /**
             * Key选择器
             */
            @Override
            public String getKey(WC value) throws Exception {
                return value.word;
            }
        })
            .countWindow(1)
            .sum("count")
            .print()
            .setParallelism(1);

        env.execute("StreamingKeyByApp");
    }


    public static class WC {
        private String word;
        private int count;

        /**
         * 无参构造 必需
         */
        public WC(){}

        public WC(String word,int count ){
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WC{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
