package com.example.ui;

import cn.hutool.core.io.FileUtil;
import com.example.domain.User;
import com.example.util.CodeUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class LoginJFrame extends JFrame implements MouseListener {

    static ArrayList<User> allUsers = new ArrayList<>();

    JButton login = new JButton();
    JButton register = new JButton();

    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextField code = new JTextField();

    // 正确的验证码
    JLabel rightCode = new JLabel();

    // 错误次数
    int errorCount = 0;

    public LoginJFrame() {
        readUserInfo();
        initJFrame();

        initView();

        // 让当前页面显示出来
        this.setVisible(true);
    }

    /**
     * 从 "userinfo.txt" 文件读取用户信息，并解析成 User 对象，然后添加到 allUsers 列表中。
     */
    private void readUserInfo() {
        List<String> userInfoStrList = FileUtil.readUtf8Lines("userinfo.txt");
        for (String str : userInfoStrList) {
            String[] userInfoArr = str.split("&");
            String[] arr1 = userInfoArr[0].split("=");
            String[] arr2 = userInfoArr[1].split("=");
            String[] arr3 = userInfoArr[2].split("=");
            User u = new User(arr1[1], arr2[1], Integer.parseInt(arr3[1]));
            allUsers.add(u);
        }
    }

    /**
     * 在这个界面添加内容
     */
    private void initView() {
        // 添加用户名字
        JLabel usernameText = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);

        // 添加用户名输入框
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        // 添加密码文字
        JLabel passwordText = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);

        // 密码输入框
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);

        // 验证码提示
        JLabel codeText = new JLabel(new ImageIcon("image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);

        // 验证码输入框
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        String codeStr = CodeUtil.getCode();
        // 设置内容
        rightCode.setText(codeStr);
        // 绑定鼠标事件
        rightCode.addMouseListener(this);
        // 位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        // 添加到界面
        this.getContentPane().add(rightCode);

        // 添加登录按钮
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        // 去除按钮的边框
        login.setBorderPainted(false);
        // 去除按钮的背景
        login.setContentAreaFilled(false);
        // 给登录按钮绑定鼠标事件
        login.addMouseListener(this);
        this.getContentPane().add(login);

        // 添加注册按钮
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        // 去除按钮的边框
        register.setBorderPainted(false);
        // 去除按钮的背景
        register.setContentAreaFilled(false);
        // 给注册按钮绑定鼠标事件
        register.addMouseListener(this);
        this.getContentPane().add(register);

        // 添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }

    /**
     * 初始化界面
     */
    private void initJFrame() {
        // 设置宽高
        this.setSize(488, 430);
        this.setTitle("拼图 登录");
        // 设置界面置顶
        this.setAlwaysOnTop(true);
        // 设置界面居中
        this.setLocationRelativeTo(null);
        // 设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 取消内部默认布局
        this.setLayout(null);
    }

    /**
     * 点击
     *
     * @param e 要处理的事件
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {
            // 获取两个文本输入框中的内容
            String usernameInput = username.getText();
            char[] passwordChars = password.getPassword();
            String passwordInput = new String(passwordChars);
            // 获取用户输入的验证码
            String codeInput = this.code.getText();

            // 创建一个User对象
            User userInfo = new User(usernameInput, passwordInput, 0);

            if (codeInput.isEmpty()) {
                showJDialog("验证码不能为空");
            } else if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                // 调用showJDialog方法并展示弹框
                showJDialog("用户名或密码不能为空");
            } else if (!codeInput.equalsIgnoreCase(rightCode.getText())) {
                showJDialog("验证码错误");
            } else if (contains(userInfo)) {
                // 关闭当前登录界面
                this.setVisible(false);
                // 打开游戏的主界面
                // 需要把当前登录的用户名传递给游戏界面
                new GameJFrame();
            } else {
                if (!containsUsername(usernameInput)) {
                    showJDialog("用户名不存在");
                    return;
                }

                if (errorCount < 3) {
                    showJDialog("用户名或密码错误，您还有" + (3 - errorCount) + "次机会");
                } else {
                    showJDialog("你的账户已锁定");
                }
            }
        } else if (e.getSource() == register) {
            this.setVisible(false);
            new RegisterJFrame(allUsers);
        } else if (e.getSource() == rightCode) {
            String code = CodeUtil.getCode();
            rightCode.setText(code);
        }
    }

    /**
     * 检查是否包含指定用户名的用户，并更新错误计数
     *
     * @param username 要检查的用户名
     * @return 如果包含指定用户名的用户则返回true，否则返回false
     */
    public boolean containsUsername(String username) {
        for (User u : allUsers) {
            if (u.getUsername().equals(username)) {
                errorCount = u.getCount();
                ++errorCount;
                u.setCount(errorCount);
                FileUtil.writeLines(allUsers, "userinfo.txt", "UTF-8");
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户在集合中是否存在。
     *
     * @param userInput 用户输入的用户对象，包含用户名和密码信息。
     * @return 如果集合中存在与用户输入相同用户名和密码的用户，则返回 true；否则返回 false。
     */
    private boolean contains(User userInput) {
        for (User rightUser : allUsers) {
            if (userInput.getUsername().equals(rightUser.getUsername()) && userInput.getPassword().equals(rightUser.getPassword()) && rightUser.getCount() < 3) {
                // 有相同的代表存在，返回true，后面的不需要再比了
                rightUser.setCount(0);
                FileUtil.writeLines(allUsers, "userinfo.txt", "UTF-8");
                return true;
            }
        }
        // 循环结束之后还没有找到就表示不存在
        return false;
    }

    /**
     * 显示一个带有指定内容的弹窗。
     *
     * @param content 弹窗中要显示的文本内容
     */
    private void showJDialog(String content) {
        // 创建一个弹窗对象
        JDialog jDialog = new JDialog();
        // 给弹窗设置大小
        jDialog.setSize(200, 150);
        // 让弹窗置顶
        jDialog.setAlwaysOnTop(true);
        // 弹窗不关闭永远无法操作下面的界面
        jDialog.setModal(true);
        // 让弹框居中
        jDialog.setLocationRelativeTo(null);
        // 创建JLabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        // 让弹框展示出来
        jDialog.setVisible(true);
    }

    /**
     * 按下不松
     *
     * @param e 要处理的事件
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按下.png"));
        }
    }

    /**
     * 松开按钮
     *
     * @param e 要处理的事件
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
