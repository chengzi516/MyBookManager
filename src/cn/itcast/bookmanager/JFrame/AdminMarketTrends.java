package cn.itcast.bookmanager.JFrame;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMarketTrends extends JFrame {

    private static final String TITLE = "市场趋势";
    private static final int WIDTH = 611;
    private static final int HEIGHT = 472;
    private final JFrame jf;

    public AdminMarketTrends() {
        super(TITLE);
        jf = this;
        try {
            setLayout(null);
            setSize(WIDTH, HEIGHT);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            init();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("类别管理");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
        mntmNewMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminMenuFrm();
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("类别修改");
        mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBTypeEdit();
            }
        });
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenu mnNewMenu_2 = new JMenu("书籍管理");
        menuBar.add(mnNewMenu_2);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
        mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBookAdd();
            }
        });
        mnNewMenu_2.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
        mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBookEdit();
            }
        });
        mnNewMenu_2.add(mntmNewMenuItem_3);

        JMenu menu1 = new JMenu("用户管理");
        menuBar.add(menu1);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("用户信息");
        mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminUserInfo();
            }
        });
        menu1.add(mntmNewMenuItem_4);

        JMenuItem mntmNewMenuItem_5 = new JMenuItem("借阅信息");
        menu1.add(mntmNewMenuItem_5);

        JMenu mnNewMenu_1 = new JMenu("退出系统");
        mnNewMenu_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "欢迎再次使用");
                jf.dispose();
            }
        });
        menuBar.add(mnNewMenu_1);

        JMenu mnNewMenu_3 = new JMenu("反馈问题处理");
        mnNewMenu_3.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new FeedBackEdit();
            }
        });
        menuBar.add(mnNewMenu_3);

        JMenu statistAnalysis = new JMenu("统计分析");
        statistAnalysis.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminStatistAnalysis();
            }
        });
        menuBar.add(statistAnalysis);

        JMenu marketTrends = new JMenu("市场趋势");
        marketTrends.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminMarketTrends();
            }
        });
        menuBar.add(marketTrends);

        JMenu userPortrait = new JMenu("用户画像");
        userPortrait.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminUserPortrait();
            }
        });
        menuBar.add(userPortrait);

        JTable table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, WIDTH - 10, HEIGHT - 65);
        add(table.getTableHeader());
        add(scrollPane);
        try (WebClient wc = new WebClient(BrowserVersion.CHROME)) {
            wc.getOptions().setJavaScriptEnabled(false);
            wc.getOptions().setCssEnabled(false);
            wc.getOptions().setThrowExceptionOnScriptError(false);
            wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
            wc.getOptions().setTimeout(3000);
            wc.getOptions().setActiveXNative(false);
            wc.getOptions().setDoNotTrackEnabled(false);
            wc.waitForBackgroundJavaScript(2000);
            wc.setAjaxController(new NicelyResynchronizingAjaxController());
            wc.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36");
            HtmlPage page = wc.getPage("http://club.chaoxing.com/");
            DomNodeList<DomNode> domNodes = page.querySelectorAll(".showCont > .clearfix");
            table.setModel(new DefaultTableModel(domNodes
                    .stream()
                    .map(domNode -> new Object[]{
                            domNode.querySelectorAll("div").get(0).getTextContent(),
                            domNode.querySelector(".text").getTextContent(),
                            domNode.querySelector(".name").getTextContent()
                    }).toArray(Object[][]::new), new Object[]{"热度", "书名", "著作"}));
        } catch (Exception ignored) {
        }
    }
}
