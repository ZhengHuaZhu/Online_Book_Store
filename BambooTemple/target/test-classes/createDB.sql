/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  zhu zhenghua
 * Created: Feb 4, 2016
 */

DROP DATABASE IF EXISTS g2w16;
CREATE DATABASE g2w16;

GRANT ALL ON g2w16.* TO g2w16@"%" IDENTIFIED BY "lamp2rover";
GRANT ALL ON g2w16.* TO g2w16@"localhost" IDENTIFIED BY "lamp2rover";
GRANT ALL ON g2w16.* TO mehdi@"%" IDENTIFIED BY "mehdi";
GRANT ALL ON g2w16.* TO mehdi@"localhost" IDENTIFIED BY "mehdi";
GRANT ALL ON g2w16.* TO zheng@"%" IDENTIFIED BY "zheng";
GRANT ALL ON g2w16.* TO zheng@"localhost" IDENTIFIED BY "zheng";
GRANT ALL ON g2w16.* TO julien@"%" IDENTIFIED BY "julien";
GRANT ALL ON g2w16.* TO julien@"localhost" IDENTIFIED BY "julien";
GRANT ALL ON g2w16.* TO derek@"%" IDENTIFIED BY "derek";
GRANT ALL ON g2w16.* TO derek@"localhost" IDENTIFIED BY "derek";
GRANT ALL ON g2w16.* TO marjorie@"%" IDENTIFIED BY "marjorie";
GRANT ALL ON g2w16.* TO marjorie@"localhost" IDENTIFIED BY "marjorie";