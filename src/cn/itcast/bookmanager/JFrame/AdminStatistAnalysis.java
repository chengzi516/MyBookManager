package cn.itcast.bookmanager.JFrame;

import cn.itcast.bookmanager.dao.BoardDao;
import cn.itcast.bookmanager.utils.DbUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 统计分析
 */
public class AdminStatistAnalysis extends JFrame {

    //窗口标题
    private static final String TITLE = "统计分析";
    //窗口宽
    private static final int WIDTH = 611;
    //窗口高
    private static final int HEIGHT = 472;
    //本类对象
    private final JFrame jf;
    //创建统计分析持久层实例
    private static final BoardDao BOARD_DAO = new BoardDao();

    /**
     * 无参构造
     */
    public AdminStatistAnalysis() {
        //调用父类构造修改窗口标题
        super(TITLE);
        //将对象赋值到成员变量中
        jf = this;
        try {
            //设置自定义布局模式
            setLayout(null);
            //设置窗口大小
            setSize(WIDTH, HEIGHT);
            //禁用窗口大小缩放
            setResizable(false);
            //窗口居中
            setLocationRelativeTo(null);
            //无线程时杀死进程
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            //初始化控件
            init();
            //显示窗口
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
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

        //面板控件
        JPanel board = new JPanel();
        //设置自定义布局模式
        board.setLayout(null);
        //将面板添加到滚动框中
        JScrollPane scrollPane = new JScrollPane(board, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //设置滚动框位置和大小
        scrollPane.setBounds(0, 0, WIDTH - 10, HEIGHT - 65);
        //将面板添加到窗口载体上
        add(scrollPane);
        //获取数据库连接传入统计分析持久层的统计方法中
        List<Map<String, Object>> result = BOARD_DAO.board(new DbUtil().getConnection());
        //设置面板大小
        board.setPreferredSize(new Dimension(WIDTH - 50, result.size() * 40 + 35));
        //计算总数
        double total = result.stream().mapToInt(obj -> Integer.parseInt(String.valueOf(obj.get("num")))).sum();
        //遍历结果集
        for (int i = 0; i < result.size(); i++) {
            //将拿到的书籍类型名称放到标签中
            JLabel typeName = new JLabel(String.valueOf(result.get(i).get("typeName")));
            //动态设置标签位置和大小
            typeName.setBounds(20, i * 40 + 20, 60, 30);
            //计算当前数据的占比
            double value = Integer.parseInt(String.valueOf(result.get(i).get("num"))) / total;
            //将书籍类型数量占比放到文本框中
            JTextField num = new JTextField(String.format("%.1f", value * 100) + "%");
            //设置文本内容右对齐
            num.setHorizontalAlignment(SwingConstants.RIGHT);
            //动态设置文本框位置和大小
            num.setBounds(typeName.getX() + typeName.getWidth(), typeName.getY() + 5, (int) ((WIDTH - 150) * value), typeName.getHeight() - 10);
            //将书籍类型数量占比放到悬浮提示框中
            num.setToolTipText(result.get(i).get("num") + " (" + String.format("%.1f", value * 100) + "%)");
            //禁用文本框的编辑功能
            num.setEnabled(false);
            //将标签和文本框添加到面板中
            addAll(board, typeName, num);
        }
        //实例标签控件
        JLabel bg = new JLabel();
        //加载背景图片
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tupian/adBG2.png")));
        //设置背景图片到标签中并设置大小和平滑比例
        bg.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(WIDTH, HEIGHT, 16)));
        //设置标签位置和大小
        bg.setBounds(0, 0, WIDTH, HEIGHT);
        //添加标签到面板中
        board.add(bg);
    }

    /**
     * 统一添加控件
     *
     * @param that
     * @param components
     */
    private void addAll(Container that, Component... components) {
        //得到控件流遍历添加到that对象中
        Arrays.stream(components).forEach(that::add);
    }

//    public static void main(String[] args) throws Exception {
//        //设置浅阴影窗口
//        BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
//        //关闭标题栏设置扳手
//        UIManager.put("RootPane.setupButtonVisible", Boolean.FALSE);
//        //开启样式注入
//        BeautyEyeLNFHelper.launchBeautyEyeLNF();
//        new AdminStatistAnalysis();
//    }
}
