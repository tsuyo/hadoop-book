#!/usr/bin/env ruby

# require 'zlib'

# for gzfile in Dir.glob('ncdc_data/*') do
# f = File.open(gzfile)
# gz = Zlib::GzipReader.new(f)
# gz.each_line do |line|
STDIN.each_line do |line|
  val = line
  year, temp, q = val[15,4], val[87,5], val[92,1]
  puts "#{year}\t#{temp}" if (temp != "+9999" && q =~ /[01459]/)
end
# gz.close
# end
