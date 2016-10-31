ln -s /opt/hadoop-2.7.3-pseudo /opt/hadoop-2.7.3
start-dfs.sh && start-yarn.sh && mr-jobhistory-daemon.sh start historyserver
export HADOOP_CLASSPATH=target/ch02-mr-intro-4.0.jar
nohup hadoop MaxTemperatureWithCombiner file:///home/ec2-user/ncdc_data file:///home/ec2-user/ch02-mr-intro/output/output_pseudo_combiner 2>&1 > ~/ch02-mr-intro/output/result_pseudo_combiner.log &
