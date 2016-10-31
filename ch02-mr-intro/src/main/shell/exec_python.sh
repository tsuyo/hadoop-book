nohup hadoop jar ${HADOOP_HOME}/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar \
-input ncdc_data -output output_python \
-mapper ch02-mr-intro/src/main/python/max_temperature_map.py \
-reducer ch02-mr-intro/src/main/python/max_temperature_reduce.py 2>&1 > results_python.log &
