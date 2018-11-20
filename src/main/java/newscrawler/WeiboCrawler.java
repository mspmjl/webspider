package newscrawler;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by 缪缪同学 on 2018/8/26.
 */
public class WeiboCrawler extends BreadthCrawler {

    String cookie;

    public WeiboCrawler(String crawlPath, boolean autoParse) throws Exception {
        super(crawlPath, autoParse);
                /* 获取新浪微博的cookie，账号密码以明文形式传输，请使用小号 */
        cookie = WeiboCN.getSinaCookie("17605235859", "IMmjl19931206");

        //设置线程数
        setThreads(3);
        //设置每个线程的爬取间隔
        setExecuteInterval(1000);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        int pageNum = Integer.valueOf(page.meta("pageNum"));
        /* 抽取微博 */
        Elements weibos = page.select("div.c[id]");
        for (Element weibo : weibos) {
            System.out.println("第" + pageNum + "页\t" + weibo.text());
        }
    }

    public static void main(String[] args) throws Exception {
        WeiboCrawler crawler = new WeiboCrawler("crawl_weibo",true);

        /* 对某人微博前5页进行爬取 */
        for (int i = 1; i <= 5; i++) {
            String seedUrl = "http://weibo.cn/zhouhongyi?vt=4&page=" + i;
            crawler.addSeed(seedUrl);
//            System.out.println(crawler);
        }
        crawler.start(1);
    }
}

