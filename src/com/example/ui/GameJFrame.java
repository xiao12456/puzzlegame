package com.example.ui;

import cn.hutool.core.io.IoUtil;
import com.example.domain.GameInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Properties;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    // 创建二位数组
    // 目的：用来管理数据
    // 加载图片的时候，会根据二位数组中的数据进行加载
    int[][] data = new int[4][4];

    // 记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    // 定义一个变量，记录当前展示图片的路径
    String path = "image\\girl\\girl1\\";

    // 定义一个二维数组，存储正确的数据
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    // 定义变量用来统计步数
    int step = 0;

    // 创建选项下面的条目对象
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");

    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenu saveJMenu = new JMenu("存档");
    JMenu loadJMenu = new JMenu("读档");

    JMenuItem saveItem0 = new JMenuItem("存档0(空)");
    JMenuItem saveItem1 = new JMenuItem("存档1(空)");
    JMenuItem saveItem2 = new JMenuItem("存档2(空)");
    JMenuItem saveItem3 = new JMenuItem("存档3(空)");
    JMenuItem saveItem4 = new JMenuItem("存档4(空)");

    JMenuItem loadItem0 = new JMenuItem("读档0(空)");
    JMenuItem loadItem1 = new JMenuItem("读档1(空)");
    JMenuItem loadItem2 = new JMenuItem("读档2(空)");
    JMenuItem loadItem3 = new JMenuItem("读档3(空)");
    JMenuItem loadItem4 = new JMenuItem("读档4(空)");

    JMenuItem accountItem = new JMenuItem("公众号");

    public GameJFrame() {
        initJFrame();

        initJMenuBar();

        initData();

        initImage();

        this.setVisible(true);
    }

    /**
     * 初始化数据（打乱）
     */
    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }


        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    /**
     * 初始化图片（根据打乱之后的结果去加载图片）
     * 添加图片的时候，就需要按照二维数组中管理的数据添加图片
     */
    private void initImage() {
        // 清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            // 显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);

        // 外循环 --- 把内循环重复执行了4次
        for (int i = 0; i < 4; i++) {
            // 内循环 --- 表示在一行添加4张图片
            for (int j = 0; j < 4; j++) {
                // 获取当前要加载图片的序号
                int num = data[i][j];
                // 创建一个 JLabel 的对象（管理容器）
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));
                // 指定图片的位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                // 给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // 把管理容器添加到界面中
                this.getContentPane().add(jLabel);
            }
        }

        // 添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        // 刷新一下界面
        this.getContentPane().repaint();
    }

    /**
     * 初始化菜单
     */
    private void initJMenuBar() {
        // 创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        // 创建菜单上面的两个选项的对象（功能 关于我们）
        JMenu functionJMenu = new JMenu("功能");

        JMenu changeImage = new JMenu("更换图片");


        // 把5个存档，添加到saveJMenu中
        saveJMenu.add(saveItem0);
        saveJMenu.add(saveItem1);
        saveJMenu.add(saveItem2);
        saveJMenu.add(saveItem3);
        saveJMenu.add(saveItem4);

        // 把5个读档，添加到loadJMenu中
        loadJMenu.add(loadItem0);
        loadJMenu.add(loadItem1);
        loadJMenu.add(loadItem2);
        loadJMenu.add(loadItem3);
        loadJMenu.add(loadItem4);

        JMenu aboutJMenu = new JMenu("关于我们");

        // 把美女，动物，运动添加到更换图片当中
        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        // 将每一个选项下面的条目添加到选项当中
        functionJMenu.add(changeImage);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        functionJMenu.add(saveJMenu);
        functionJMenu.add(loadJMenu);

        aboutJMenu.add(accountItem);

        // 添加更换图片的事件监听
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        // 给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);

        accountItem.addActionListener(this);

        saveItem0.addActionListener(this);
        saveItem1.addActionListener(this);
        saveItem2.addActionListener(this);
        saveItem3.addActionListener(this);
        saveItem4.addActionListener(this);
        loadItem0.addActionListener(this);
        loadItem1.addActionListener(this);
        loadItem2.addActionListener(this);
        loadItem3.addActionListener(this);
        loadItem4.addActionListener(this);

        // 将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        getGameInfo();

        // 给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    /**
     * 获取游戏存档信息并更新菜单项显示
     */
    public void getGameInfo() {
        File file = new File("save");
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            int step = gi.getStep();

            String name = f.getName();
            int index = name.charAt(4) - '0';

            saveJMenu.getItem(index).setText("存档" + index + "(" + step + "步)");
            loadJMenu.getItem(index).setText("存档" + index + "(" + step + "步)");
        }
    }

    /**
     * 初始化界面
     */
    private void initJFrame() {
        this.setSize(603, 680);
        this.setTitle("拼图单机版 v1.0");
        // 设置界面置顶
        this.setAlwaysOnTop(true);
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 取消默认的居中放置，只有取消了才会按照 XY 轴的形式添加组件
        this.setLayout(null);
        // 给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 按下不松时会调用这个方法
     *
     * @param e 要处理的事件
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) {
            // 把界面中所有的图片全部删除
            this.getContentPane().removeAll();
            // 加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            // 加载背景图片
            JLabel background = new JLabel(new ImageIcon("image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);

            // 刷新一下界面
            this.getContentPane().repaint();
        }
    }

    /**
     * 松开按键时会调用这个方法
     *
     * @param e 要处理的事件
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // 判断游戏是否胜利，如果胜利，此方法需要直接结束，不能再执行下面的移动代码了
        if (victory()) {
            // 结束方法
            return;
        }
        // 对上，下，左，右进行判断
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            if (x == 3) {
                // 表示空白块已经在最下方了，他的下面没有图片能移动了
                return;
            }
            // 把空白方块下方的数字往上移动
            // x,y 表示空白方块
            // x+1,y 表示空白方块下的数字

            // 把空白方块下方的数字赋值给空白方块
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            // 每移动一次，计数器就自增一次
            step++;
            // 调用方法按照最新的数字加载图片
            initImage();
        } else if (code == KeyEvent.VK_DOWN) {
            if (x == 0) {
                // 表示空白块已经在最上方了，他的上面没有图片能移动了
                return;
            }
            // 把空白方块上方的数字往下移动
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            // 每移动一次，计数器就自增一次
            step++;
            // 调用方法按照最新的数字加载图片
            initImage();
        } else if (code == KeyEvent.VK_LEFT) {
            if (y == 3) {
                // 表示空白块已经在最右方了，他的右面没有图片能移动了
                return;
            }
            // 把空白方块右方的数字往左移动
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            // 每移动一次，计数器就自增一次
            step++;
            // 调用方法按照最新的数字加载图片
            initImage();
        } else if (code == KeyEvent.VK_RIGHT) {
            if (y == 0) {
                // 表示空白块已经在最左方了，他的左面没有图片能移动了
                return;
            }
            // 把空白方块左方的数字往右移动
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            // 每移动一次，计数器就自增一次
            step++;
            // 调用方法按照最新的数字加载图片
            initImage();
        } else if (code == KeyEvent.VK_A) {
            initImage();
        } else if (code == KeyEvent.VK_W) {
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    /**
     * 判断data数组中的数据是否与win数组中相同。
     *
     * @return 如果数组完全相同则返回true，否则返回false。
     */
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            // i:依次二位数组data里面的索引
            // data[i]:依次表示每一个一位数组
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    // 只要有一个数据不一样，则返回false
                    return false;
                }
            }
        }
        // 循环结束表示数组遍历比较完毕，全都一样返回true
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取当前被点击的条目对象
        Object obj = e.getSource();
        if (obj == replayItem) {
            // 计步器清零
            step = 0;
            // 再次打乱二维数组中的数据
            initData();
            // 重新加载图片
            initImage();
        } else if (obj == reLoginItem) {
            // 关闭登录界面
            this.setVisible(false);
            // 打开登录界面
            new LoginJFrame();
        } else if (obj == closeItem) {
            // 直接关闭虚拟机即可
            System.exit(0);
        } else if (obj == accountItem) {

            Properties prop = new Properties();
            try {
                FileInputStream fis=new FileInputStream("game.properties");
                prop.load(fis);
                fis.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            String path = (String) prop.get("account");

            // 创建一个弹窗对象
            JDialog jDialog = new JDialog();
            // 创建一个管理图片的容器对象JLabel
            JLabel jLabel = new JLabel(new ImageIcon(path));
            // 设置位置和宽高
            jLabel.setBounds(0, 0, 258, 258);
            // 将图片添加到弹框当中
            jDialog.getContentPane().add(jLabel);
            // 给弹窗设置大小
            jDialog.setSize(344, 344);
            // 让弹窗置顶
            jDialog.setAlwaysOnTop(true);
            // 让弹窗居中
            jDialog.setLocationRelativeTo(null);
            // 弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            // 让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj == girl) {
            path = "image\\girl\\girl" + (new Random().nextInt(13) + 1) + "\\";
            initData();
            initImage();
        } else if (obj == animal) {
            path = "image\\animal\\animal" + (new Random().nextInt(8) + 1) + "\\";
            initData();
            initImage();
        } else if (obj == sport) {
            path = "image\\sport\\sport" + (new Random().nextInt(10) + 1) + "\\";
            initData();
            initImage();
        } else if (obj == saveItem0 || obj == saveItem1 || obj == saveItem2 || obj == saveItem3 || obj == saveItem4) {
            JMenuItem item = (JMenuItem) obj;
            String str = item.getText();
            int index = str.charAt(2) - '0';

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save\\save" + index + ".data"));
                GameInfo gi = new GameInfo(data, x, y, path, step);
                IoUtil.writeObj(oos, true, gi);

                item.setText("存档" + index + "(" + step + "步)");
                loadJMenu.getItem(index).setText("存档" + index + "(" + step + "步)");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } else if (obj == loadItem0 || obj == loadItem1 || obj == loadItem2 || obj == loadItem3 || obj == loadItem4) {
            JMenuItem item = (JMenuItem) obj;
            String str = item.getText();
            int index = str.charAt(2) - '0';

            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save\\save" + index + ".data"));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            data = gi.getData();
            step = gi.getStep();
            path = gi.getPath();
            x = gi.getX();
            y = gi.getY();

            initImage();
        }
    }
}
