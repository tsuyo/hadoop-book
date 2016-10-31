nohup hadoop jar ${HADOOP_HOME}/share/hadoop/tools/lib/hadoop-streaming-*.jar \
-files ch02-mr-intro/src/main/ruby/max_temperature_map.rb,ch02-mr-intro/src/main/ruby/max_temperature_reduce.rb \
-input ncdc_data -output output_ruby_combiner \
-mapper ch02-mr-intro/src/main/ruby/max_temperature_map.rb \
-combiner ch02-mr-intro/src/main/ruby/max_temperature_reduce.rb \
-reducer ch02-mr-intro/src/main/ruby/max_temperature_reduce.rb 2>&1 > results_ruby_combiner.log &
