wget -P /tmp https://packages.chef.io/files/stable/chef-server/12.17.33/ubuntu/16.04/chef-server-core_12.17.33-1_amd64.deb
sudo dpkg -i /tmp/chef-server-core_12.17.33-1_amd64.deb
sudo chef-server-ctl install chef-manage
sudo chef-server-ctl reconfigure
sudo chef-server-ctl user-create feliperojas felipe rojas william.rojas@bizagi.com 'Bizagi123*/' --filename ~/auth.pem
sudo chef-server-ctl org-create bizdevopstest 'bizagi devops test' --association_user feliperojas --filename ~/bizdevopstest.pem
#sudo chef-manage-ctl reconfigure