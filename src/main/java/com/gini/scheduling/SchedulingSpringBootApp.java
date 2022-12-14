package com.gini.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gini.scheduling.controller.SchedulingController;
import com.gini.scheduling.controller.SgrroomController;
import com.gini.scheduling.dao.SgruserRepository;
import com.gini.scheduling.dao.SgshiftRepository;
import com.gini.scheduling.dao.SgsysRepository;
import com.gini.scheduling.model.Sgruser;
import com.gini.scheduling.model.Sgshift;
import com.gini.scheduling.model.Sgsys;
import com.gini.scheduling.utils.VacationDayCalculate;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;

@SpringBootApplication
public class SchedulingSpringBootApp extends SpringBootServletInitializer {
    public static final Logger logger = LoggerFactory.getLogger(SchedulingSpringBootApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SchedulingSpringBootApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SchedulingSpringBootApp.class);
    }

    @Bean
    public CommandLineRunner initData(SgruserRepository sgruserRepository, SgshiftRepository sgshiftRepository, SgsysRepository sgsysRepository) {
        return (args) -> {
//            // ????????????
//                List<Sgruser> sgruserList = sgruserRepository.findAll();
//                List<Sgruser> newSgruserList = new ArrayList<>();
//                for (int i = 0; i<sgruserList.size(); i++) {
//                    if(i>=0 && i<7){
//                        sgruserList.get(i).setUteam("A");
//                    }else if (i>=7 && i<14){
//                        sgruserList.get(i).setUteam("B");
//                    }else{
//                        sgruserList.get(i).setUteam("C");
//                    }
//                    newSgruserList.add(sgruserList.get(i));
//                }
//                sgruserRepository.saveAll(newSgruserList);

            // ????????????????????????
            List<Sgsys> sgsysList = new ArrayList<>();
            Map<String, String> sgsys = new HashMap<>();
            // 55(8-16)
            sgsys.put("r55RoomOpen", "5");
            sgsys.put("r55NeedManpower", "2");
            sgsys.put("r55Holiday", "1");
            sgsys.put("r55HolidayA", "1");
            sgsys.put("r55HolidayB", "1");
            sgsys.put("r55HolidayC", "1");
            sgsys.put("r55Wait", "1");
            sgsys.put("r55Nurse", "1");
            sgsys.put("r55WorkStat", "1");
            sgsys.put("r55OPDOR", "0");
            // D6(16-00)
            sgsys.put("rd6ManpowerA", "1");
            sgsys.put("rd6ManpowerB", "1");
            sgsys.put("rd6ManpowerC", "1");
            sgsys.put("rd6Manpower", "2");
            sgsys.put("rd6Holiday", "1");
            // A0(00-08)
            sgsys.put("ra0ManpowerA", "1");
            sgsys.put("ra0ManpowerB", "1");
            sgsys.put("ra0ManpowerC", "1");
            sgsys.put("ra0Manpower", "2");
            sgsys.put("ra0Holiday", "1");
            // A8(12-20)
            sgsys.put("ra8Manpower", "2");
            sgsys.put("ra8ManpowerA", "2");
            sgsys.put("ra8ManpowerB", "2");
            sgsys.put("ra8ManpowerC", "1");
            // ????????????
            sgsys.put("generalBetweenHour", "11");
            for (Map.Entry<String, String> entry : sgsys.entrySet()) {
                sgsysList.add(new Sgsys(entry.getKey(), entry.getValue()));
            }
            sgsysRepository.saveAll(sgsysList);

            // ??????????????????
            List<Sgshift> sgshiftList = new ArrayList<>();
            String[] clsnoArray = new String[]{
                "55", "D6", "A0", "A8", "OFF"};
            for (String clsno : clsnoArray) {
                sgshiftList.add(new Sgshift(clsno));
            }
            sgshiftRepository.saveAll(sgshiftList);

        };
    }
}
