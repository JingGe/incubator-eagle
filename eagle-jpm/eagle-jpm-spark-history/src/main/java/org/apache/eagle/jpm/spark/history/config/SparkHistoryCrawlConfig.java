/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  * contributor license agreements.  See the NOTICE file distributed with
 *  * this work for additional information regarding copyright ownership.
 *  * The ASF licenses this file to You under the Apache License, Version 2.0
 *  * (the "License"); you may not use this file except in compliance with
 *  * the License.  You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.apache.eagle.jpm.spark.history.config;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.Serializable;

/**
 * Created by jnwang on 2016/5/3.
 */
public class SparkHistoryCrawlConfig implements Serializable{
    public ZKStateConfig zkStateConfig;
    public JobHistoryEndpointConfig jobHistoryConfig;
    public HDFSConfig hdfsConfig;
    public BasicInfo info;
    public EagleInfo eagleInfo;
    public StormConfig stormConfig;

    public SparkHistoryCrawlConfig() {
        Config config = ConfigFactory.load();

        this.zkStateConfig = new ZKStateConfig();
        this.zkStateConfig.zkQuorum = config.getString("dataSourceConfig.zkQuorum");
        this.zkStateConfig.zkRetryInterval = config.getInt("dataSourceConfig.zkRetryInterval");
        this.zkStateConfig.zkRetryTimes = config.getInt("dataSourceConfig.zkRetryTimes");
        this.zkStateConfig.zkSessionTimeoutMs = config.getInt("dataSourceConfig.zkSessionTimeoutMs");
        this.zkStateConfig.zkRoot = config.getString("dataSourceConfig.zkRoot");

        this.jobHistoryConfig = new JobHistoryEndpointConfig();
        jobHistoryConfig.historyServerUrl = config.getString("dataSourceConfig.spark.history.server.url");
        jobHistoryConfig.historyServerUserName = config.getString("dataSourceConfig.spark.history.server.username");
        jobHistoryConfig.historyServerUserPwd = config.getString("dataSourceConfig.spark.history.server.pwd");
        jobHistoryConfig.rms = config.getStringList("dataSourceConfig.rm.url").toArray(new String[0]);

        this.hdfsConfig = new HDFSConfig();
        this.hdfsConfig.baseDir = config.getString("dataSourceConfig.hdfs.baseDir");
        this.hdfsConfig.endpoint = config.getString("dataSourceConfig.hdfs.endPoint");
        this.hdfsConfig.principal = config.getString("dataSourceConfig.hdfs.principal");
        this.hdfsConfig.keytab = config.getString("dataSourceConfig.hdfs.keytab");

        this.info = new BasicInfo();
        info.site = String.format("%s-%s",config.getString("basic.cluster"),config.getString("basic.datacenter"));
        info.jobConf = config.getStringList("basic.jobConf.additional.info").toArray(new String[0]);

        this.eagleInfo = new EagleInfo();
        this.eagleInfo.host = config.getString("eagleProps.eagle.service.host");
        this.eagleInfo.port = config.getInt("eagleProps.eagle.service.port");

        this.stormConfig = new StormConfig();
        this.stormConfig.topologyName = config.getString("storm.name");
        this.stormConfig.workerNo = config.getInt("storm.workerNo");
        this.stormConfig.timeoutSec = config.getInt("storm.messageTimeoutSec");
        this.stormConfig.spoutPending = config.getInt("storm.pendingSpout");

    }


    public static class ZKStateConfig implements Serializable {
        public String zkQuorum;
        public String zkRoot;
        public int zkSessionTimeoutMs;
        public int zkRetryTimes;
        public int zkRetryInterval;
    }

    public static class JobHistoryEndpointConfig implements Serializable {
        public String[] rms;
        public String historyServerUrl;
        public String historyServerUserName;
        public String historyServerUserPwd;
    }

    public static class HDFSConfig implements Serializable {
        public String endpoint;
        public String baseDir;
        public String principal;
        public String keytab;
    }

    public static class BasicInfo implements Serializable {
        public String site;
        public String[] jobConf;
    }

    public static class StormConfig implements Serializable {
        public int workerNo;
        public int timeoutSec;
        public String topologyName;
        public int spoutPending;
    }

    public static class EagleInfo implements Serializable {
        public String host;
        public int port;
    }


}