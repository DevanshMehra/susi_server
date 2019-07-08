wget http://download.susi.ai/susi_server/susi_server_binary_latest.tar.gz
tar -xzf susi_server_binary_latest.tar.gz
rm -rf bin build conf html systemd
mv susi_server_binary_latest/* .
rmdir susi_server_binary_latest/
rm susi_server_binary_latest.tar.gz
