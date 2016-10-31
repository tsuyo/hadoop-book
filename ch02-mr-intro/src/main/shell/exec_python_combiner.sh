nohup hadoop jar ${HADOOP_HOME}/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
-input ncdc_data -output output_python_combiner \
-mapper ch02-mr-intro/src/main/python/max_temperature_map.py \
-combiner ch02-mr-intro/src/main/python/max_temperature_reduce.py \
-reducer ch02-mr-intro/src/main/python/max_temperature_reduce.py 2>&1 > results_python_combiner.log &
