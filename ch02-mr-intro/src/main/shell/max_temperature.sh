#!/usr/bin/env bash

DATE_FORMAT='%Y%m%d_%H%M%S'

echo $(date +${DATE_FORMAT})
for year in ncdc_data/*
do
  begin_t=$(date +${DATE_FORMAT})
  echo -ne `basename $year .gz`"\t"
  gunzip -c $year | \
    awk '{ temp = substr($0, 88, 5) + 0;
           q = substr($0, 93, 1);
           if (temp != 9999 && q ~ /[01459]/ && temp > max) max = temp }
         END { printf "%d", max }'
  echo -e "\t${begin_t}-$(date +${DATE_FORMAT})"
done
echo $(date +${DATE_FORMAT})
