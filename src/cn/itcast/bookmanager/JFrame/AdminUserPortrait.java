package cn.itcast.bookmanager.JFrame;

import cn.itcast.bookmanager.dao.UserDao;
import cn.itcast.bookmanager.model.User;
import cn.itcast.bookmanager.utils.DbUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminUserPortrait extends JFrame {

    private static final String TITLE = "用户画像";
    private static final int WIDTH = 611;
    private static final int HEIGHT = 472;
    private final JFrame jf;
    private static final UserDao USER_DAO = new UserDao();
    private static final List<JLabel> LABELS = new ArrayList<>();

    public AdminUserPortrait() {
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

    private void init() throws Exception {
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

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, WIDTH - 10, HEIGHT - 65);
        add(panel);

        ResultSet rs = USER_DAO.list(new DbUtil().getConnection(), new User());
        int x = 0, y = 0;
        while (rs.next()) {
            UserLabel userLabel = new UserLabel(rs.getInt("id"), rs.getString("username"));
            userLabel.setBounds(x * 60 + 10, y * 30 + 10, 60, 30);
            userLabel.addActionListener(e -> {
                try {
                    LABELS.forEach(panel::remove);
                    List<Map<String, Object>> portrait = USER_DAO.findUserPortrait(new DbUtil().getConnection(), userLabel.id);
                    for (Map<String, Object> map : portrait) {
                        JLabel label = new JLabel(String.valueOf(map.get("typeName")));
                        label.setBounds((int) (Math.random() * (WIDTH - 110)), (int) (Math.floor(Math.random() * (HEIGHT - 230) + 65)), 100, 40);
                        label.setFont(new Font("幼圆", Font.BOLD, Integer.parseInt(String.valueOf(map.get("num"))) + 14));
                        label.setForeground(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)));
                        panel.add(label);
                        LABELS.add(label);
                    }
                    panel.repaint();
                } catch (Exception ignored) {
                }
            });
            panel.add(userLabel);
            if (userLabel.getX() + userLabel.getWidth() * 2 > WIDTH - 10) {
                x = 0;
                y++;
            } else {
                x++;
            }
        }
    }

    static class UserLabel extends JButton {
        private final Integer id;

        public UserLabel(Integer id, String username) {
            this.id = id;
            setText(username);
            setBorder(null);
            setFocusPainted(false);
        }
    }
}
