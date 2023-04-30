package com.kbd.PAB;


import com.kbd.PAB.Crawling.CpuInfoCrawlingService;
import com.kbd.PAB.Crawling.MbInfoCrawlingService;
import com.kbd.PAB.Service.*;
import com.kbd.PAB.VO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })
@EntityScan(basePackages = { "com.kbd.PAB.repository", "com.kbd.PAB.service", "com.kbd.PAB.vo" })



@Controller
public class MainController {

    @RequestMapping("/test")
    public String home(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, Model model) {
        int MaxPageNum = 10; // 나중에 DB에서 받아오는걸로 처리

        model.addAttribute("pageNum", pageNum);
        model.addAttribute("maxPageNum", MaxPageNum);

        return "test";
    }

    @Autowired
    CpuService cpuService;

    @RequestMapping("/cpuc")
    public String cpuc(Model model) throws Exception {
        ArrayList<String> urls = new ArrayList<String>(Arrays.asList("https://www.amazon.com/-/ko/dp/B07KY8HK5J/ref=sr_1_2?crid=11VZVWZTIY2ZF&keywords=Computer+CPU+processor+AM4&qid=1682850347&sprefix=computer+cpu+processor+am%2Caps%2C269&sr=8-2")); //url에 추가하기

        ArrayList<CpuVO> cpuVos = new ArrayList<CpuVO>();
        for(int i=0; i < urls.size(); i++) {
            System.out.println(i + "번째 url 크롤링중");
            CpuInfoCrawlingService crawlingService = new CpuInfoCrawlingService(urls.get(i));
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            try { //캡챠 발생시 계속 진행
            CpuVO cpuVO = new CpuVO(temp);
            cpuVos.add(cpuVO);
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
            System.out.println(i + "번째 정보 추가 완료");
        }

        cpuService.insertCPU(cpuVos);
        return "index";
    }

    @Autowired
    MbService mbService;

    @RequestMapping("/mbc")
    public String mbc(Model model) throws Exception {
        ArrayList<String> urls = new ArrayList<String>(Arrays.asList("https://www.amazon.com/-/ko/dp/B0B29H7DJS/ref=sr_1_1?c=ts&keywords=Computer%2Bmotherboard%2B(motherboard)&qid=1681132974&s=pc&sr=1-1&ts_id=1048424&th=1",
                "https://www.amazon.com/-/ko/dp/B08F7BHDLY/ref=sr_1_2?c=ts&keywords=Computer%2Bmotherboard%2B(motherboard)&qid=1681132974&s=pc&sr=1-2&ts_id=1048424&th=1",
                "https://www.amazon.com/-/ko/dp/B08VL2GRS9/ref=sr_1_7?c=ts&keywords=Computer+motherboard+%28motherboard%29&qid=1681133055&refinements=p_n_feature_browse-bin%3A33224542011%2Cp_89%3AASUS&rnid=2528832011&s=pc&sr=1-7&ts_id=1048424",
                "https://www.amazon.com/-/ko/dp/B0BZTB87BY/ref=sr_1_15?c=ts&keywords=Computer+motherboard+%28motherboard%29&qid=1681133097&refinements=p_n_feature_browse-bin%3A33224542011%2Cp_89%3AASUS&rnid=2528832011&s=pc&sr=1-15&ts_id=1048424",
                "https://www.amazon.com/dp/B08MTKJ6HM/ref=sr_1_3?c=ts&keywords=Computer+motherboard+%28motherboard%29&qid=1681133131&refinements=p_89%3AASUS%2Cp_n_feature_browse-bin%3A31268233011&rnid=31268155011&s=pc&sr=1-3&ts_id=1048424",
                "https://www.amazon.com/-/ko/dp/B0BHMW8R6S/ref=sr_1_1?keywords=computer+motherboard+%28motherboard%29+am5&qid=1681133161&s=pc&sr=1-1",
                "https://www.amazon.com/-/ko/dp/B0BFBRPBF5/ref=sr_1_4?crid=259DEYLO6IM0W&keywords=computer+motherboard+%28motherboard%29+am5&qid=1681133219&sprefix=computer+motherboard+motherboard+am5%2Caps%2C256&sr=8-4",
                "https://www.amazon.com/-/ko/dp/B0BHMVFS77/ref=sr_1_16?crid=259DEYLO6IM0W&keywords=computer+motherboard+%28motherboard%29+am5&qid=1681133247&refinements=p_89%3AASUS&rnid=2528832011&s=electronics&sprefix=computer+motherboard+motherboard+am5%2Caps%2C256&sr=1-16")); //url에 추가하기

        ArrayList<MbVO> mbVos = new ArrayList<MbVO>();
        for(int i=0; i < urls.size(); i++) {
            System.out.println(i + "번째 url 크롤링중");
            MbInfoCrawlingService crawlingService = new MbInfoCrawlingService(urls.get(i));
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            try { //캡챠 발생시 계속 진행
                MbVO mbVO = new MbVO(temp);
                mbVos.add(mbVO);
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
            System.out.println(i + "번째 정보 추가 완료");
        }

        mbService.insertCPU(mbVos);
        return "mbc";
    }

    @Autowired
    RamService ramService;

    @RequestMapping("/ramc")
    public String ramc(Model model) throws Exception {
        ArrayList<String> urls = new ArrayList<String>(Arrays.asList("https://www.amazon.com/-/ko/dp/B09PTGZM6Y/ref=sr_1_1?keywords=Computer%2Bmemory&qid=1681133866&refinements=p_89%3AG.Skill%7CSAMSUNG&rnid=2528832011&s=pc&sr=1-1&th=1",
                "https://www.amazon.com/-/ko/dp/B08176KLZT/ref=sr_1_12?keywords=Computer+memory&qid=1681134830&refinements=p_89%3AG.Skill%7CSAMSUNG&rnid=2528832011&s=pc&sr=1-12",
                "https://www.amazon.com/-/ko/dp/B088CTJLHP/ref=sr_1_18?keywords=Computer+memory&qid=1681134830&refinements=p_89%3AG.Skill%7CSAMSUNG&rnid=2528832011&s=pc&sr=1-18",
                "https://www.amazon.com/dp/B07F6L771D/ref=sr_1_5?keywords=Computer+memory&qid=1681134887&refinements=p_89%3ASAMSUNG%2Cp_n_feature_seven_browse-bin%3A24084329011%2Cp_n_feature_four_browse-bin%3A10656894011&rnid=2057428011&s=pc&sr=1-5",
                "https://www.amazon.com/-/ko/dp/B079T94WCT/ref=sr_1_17?keywords=computer+memory+25600&qid=1681134958&refinements=p_89%3ASAMSUNG&rnid=2528832011&s=pc&sr=1-17")); //url에 추가하기

        ArrayList<RamVO> ramVos = new ArrayList<RamVO>();
        for(int i=0; i < urls.size(); i++) {
            System.out.println(i + "번째 url 크롤링중");
            MbInfoCrawlingService crawlingService = new MbInfoCrawlingService(urls.get(i));
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            try { //캡챠 발생시 계속 진행
                RamVO ramVO = new RamVO(temp);
                ramVos.add(ramVO);
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
            System.out.println(i + "번째 정보 추가 완료");
        }

       ramService.insertCPU(ramVos);
        return "mbc";
    }

    @Autowired
    GpuService gpuService;

    @RequestMapping("/gpuc")
    public String gpuc(Model model) throws Exception {
        ArrayList<String> urls = new ArrayList<String>(Arrays.asList("https://www.amazon.com/-/ko/dp/B08WPRMVWB/ref=sr_1_1?c=ts&keywords=Computer+graphics+card&qid=1681135140&s=pc&sr=1-1&ts_id=284822",
                "https://www.amazon.com/-/ko/dp/B0BQCVTSR3/ref=sr_1_14?c=ts&keywords=Computer+graphics+card&qid=1681135140&s=pc&sr=1-14&ts_id=284822",
                "https://www.amazon.com/-/ko/dp/B09R2NWCV1/ref=sr_1_22?c=ts&keywords=Computer+graphics+card&qid=1681135140&s=pc&sr=1-22&ts_id=284822",
                "https://www.amazon.com/-/ko/dp/B09CBYXJD9/ref=sr_1_1?crid=1MY18BSJU4H8F&keywords=3070&qid=1681136642&sprefix=30%2Caps%2C278&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B0BQ921V81/ref=sr_1_1?crid=HOWCRYOXJ1LA&keywords=3070ti&qid=1681136655&sprefix=3070t%2Caps%2C257&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B0995S7548/ref=sr_1_1?crid=V8AB0PALZQZI&keywords=3080&qid=1681136667&sprefix=30%2Caps%2C262&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B09CQKTL31/ref=sr_1_1?crid=1X1VR1DQNYXJ9&keywords=3080ti&qid=1681136682&sprefix=3080ti%2Caps%2C284&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B08ZL6XD9H/ref=sr_1_1?crid=3NUM481MU3BB5&keywords=3090&qid=1681136697&sprefix=309%2Caps%2C277&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B09BZP8KZG/ref=sr_1_1?crid=2JRUT30VWN93X&keywords=6900xt&qid=1681136725&sprefix=6900x%2Caps%2C271&sr=8-1",
                "https://www.amazon.com/-/ko/dp/B0BQCVTSR3/ref=sr_1_2?crid=W0R3LPC547KK&keywords=4070&qid=1681136740&sprefix=40%2Caps%2C270&sr=8-2",
                "https://www.amazon.com/-/ko/dp/B0BLGQHS53/ref=sr_1_2?crid=7FL6P7SFGFD3&keywords=4080&qid=1681136753&sprefix=408%2Caps%2C269&sr=8-2",
                "https://www.amazon.com/-/ko/dp/B0BG92GY61/ref=sr_1_2?crid=11OF4IARI2RVG&keywords=4090&qid=1681136762&sprefix=409%2Caps%2C268&sr=8-2")); //url에 추가하기

        ArrayList<GpuVO> gpuVos = new ArrayList<GpuVO>();
        for(int i=0; i < urls.size(); i++) {
            System.out.println(i + "번째 url 크롤링중");
            MbInfoCrawlingService crawlingService = new MbInfoCrawlingService(urls.get(i));
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            try { //캡챠 발생시 계속 진행
                GpuVO gpuVO = new GpuVO(temp);
                gpuVos.add(gpuVO);
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
            System.out.println(i + "번째 정보 추가 완료");
        }

        gpuService.insertCPU(gpuVos);
        return "mbc";
    }

    @Autowired
    PowService powService;

    @RequestMapping("/powc")
    public String powc(Model model) throws Exception {
        ArrayList<String> urls = new ArrayList<String>(Arrays.asList("https://www.amazon.com/-/ko/dp/B088SSX883/ref=sr_1_5?c=ts&keywords=computer%2Bpower%2Bsupply&qid=1681137322&s=pc&sr=1-5&ts_id=1161760&th=1&language=en_US&currency=USD",
                "https://www.amazon.com/-/ko/dp/B09425ZHVZ/ref=sr_1_8?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-8&ts_id=1161760",
                "https://www.amazon.com/-/ko/dp/B07WDLTGC1/ref=sr_1_10?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-10&ts_id=1161760",
                "https://www.amazon.com/-/ko/dp/B08DHD3RMH/ref=sr_1_13?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-13&ts_id=1161760",
                "https://www.amazon.com/-/ko/dp/B08W47MX46/ref=sr_1_18?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-18&ts_id=1161760",
                "https://www.amazon.com/-/ko/dp/B09VKF3CFC/ref=sr_1_20?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-20&ts_id=1161760",
                "https://www.amazon.com/dp/B07TJYNGB8/ref=sr_1_22_sspa?c=ts&keywords=computer+power+supply&qid=1681149185&s=pc&sr=1-22-spons&ts_id=1161760&psc=1&spLa=ZW5jcnlwdGVkUXVhbGlmaWVyPUEyN05CNURLUFVHTE4zJmVuY3J5cHRlZElkPUEwMzY0MzI5MlBIUlFTQUY2R1ZBMiZlbmNyeXB0ZWRBZElkPUEwMzc4Mzc2M0FNNk5JVVlDOENRTCZ3aWRnZXROYW1lPXNwX2J0ZiZhY3Rpb249Y2xpY2tSZWRpcmVjdCZkb05vdExvZ0NsaWNrPXRydWU=",
                "https://www.amazon.com/-/ko/dp/B08PCB2GM4/ref=sr_1_28?c=ts&keywords=computer+power+supply&qid=1681149282&s=pc&sr=1-28&ts_id=1161760")); //url에 추가하기

        ArrayList<PowerVO> powVos = new ArrayList<PowerVO>();
        for(int i=0; i < urls.size(); i++) {
            System.out.println(i + "번째 url 크롤링중");
            MbInfoCrawlingService crawlingService = new MbInfoCrawlingService(urls.get(i));
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            try { //캡챠 발생시 계속 진행
                PowerVO powVO = new PowerVO(temp);
                powVos.add(powVO);
            } catch(IndexOutOfBoundsException e) {
                continue;
            }
            System.out.println(i + "번째 정보 추가 완료");
        }

        powService.insertCPU(powVos);
        return "mbc";
    }
    @RequestMapping("/crTest")
    public String crTest(Model model) throws Exception {
            MbInfoCrawlingService crawlingService = new MbInfoCrawlingService("https://www.amazon.com/-/ko/dp/B088SSX883/ref=sr_1_5?c=ts&keywords=computer%2Bpower%2Bsupply&qid=1681137322&s=pc&sr=1-5&ts_id=1161760&th=1");
            ArrayList<String> temp = crawlingService.getInfoFromWebPage();
            model.addAttribute("info", temp);
        return "mbc";
    }

    @RequestMapping("/linkTest")
    public String linkTest(Model model) throws Exception {
        ramService.insertAmazonImgLink();
        return "mbc";
    }

    @RequestMapping("/newEstimate")
    public String newEstimate() {
        return "newEstimate";
    }

    @RequestMapping("/testAPI")
    public String testAPI() {
        return "testAPI";
    }

    @Autowired
    private PartCategoryService partCategoryService;


    @RequestMapping("/test3")
    public String test3(Model model) {
        model.addAttribute("categorys",partCategoryService.getCategory());
        return "test3";
    }

}
