package com.example.ui;

import cn.hutool.core.io.FileUtil;
import com.example.domain.User;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RegisterJFrame extends JFrame implements MouseListener {

    ArrayList<User> allUsers;

    //提升三个输入框的变量的作用范围，让这三个变量可以在本类中所有方法里面可以使用。
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField rePassword = new JTextField();

    //提升两个按钮变量的作用范围，让这两个变量可以在本类中所有方法里面可以使用。
    JButton submit = new JButton();
    JButton reset = new JButton();
    //只创建一个弹框对象
    JDialog jDialog = new JDialog();

    public RegisterJFrame(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
        initFrame();
        initView();
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == submit) {
            if (username.getText().isEmpty() || password.getText().isEmpty() || rePassword.getText().isEmpty()) {
                showDialog("用户名和密码不能为空");
                return;
            }

            if (!password.getText().equals(rePassword.getText())) {
                showDialog("两次输入的密码不一致");
                return;
            }

            if (!username.getText().matches("[a-zA-Z0-9]{4,16}")) {
                showDialog("用户名格式不正确");
                return;
            }

            if (!password.getText().matches("\\S*(?=\\S{6,})(?=\\S*[a-z])\\S*")) {
                showDialog("密码必须包含至少一个小写字母、一个数字，长度至少六位");
                return;
            }

            if (containsUsername(username.getText())) {
                showDialog("该用户名已被注册");
                return;
            }

            User user = new User(username.getText(), password.getText(), 0);
            allUsers.add(user);
            FileUtil.writeLines(allUsers, "userinfo.txt", "UTF-8");
            showDialog("注册成功");
            this.setVisible(false);
            new LoginJFrame();
        } else if (e.getSource() == reset) {
            username.setText("");
            password.setText("");
            rePassword.setText("");
        }
    }

    /**
     * 检查是否包含指定用户名的用户
     *
     * @param username 要检查的用户名
     * @return 如果包含指定用户名的用户则返回true，否则返回false
     */
    public boolean containsUsername(String username) {
        for (User u : allUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理鼠标按下事件的方法。
     * 当鼠标按下时，根据事件源设置按钮图标为按下状态。
     *
     * @param e 鼠标按下事件对象
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon("image\\register\\注册按下.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("image\\register\\重置按下.png"));
        }
    }

    /**
     * 处理鼠标释放事件的方法。
     * 当鼠标释放时，根据事件源设置按钮图标为释放状态。
     *
     * @param e 鼠标释放事件对象
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * 初始化注册界面的视图，包括用户名、密码、再次输入密码的文本标签、输入框以及注册和重置按钮。
     */
    private void initView() {
        //添加注册用户名的文本
        JLabel usernameText = new JLabel(new ImageIcon("image\\register\\注册用户名.png"));
        usernameText.setBounds(85, 135, 80, 20);

        //添加注册用户名的输入框
        username.setBounds(195, 134, 200, 30);

        //添加注册密码的文本
        JLabel passwordText = new JLabel(new ImageIcon("image\\register\\注册密码.png"));
        passwordText.setBounds(97, 193, 70, 20);

        //添加密码输入框
        password.setBounds(195, 195, 200, 30);

        //添加再次输入密码的文本
        JLabel rePasswordText = new JLabel(new ImageIcon("image\\register\\再次输入密码.png"));
        rePasswordText.setBounds(64, 255, 95, 20);

        //添加再次输入密码的输入框
        rePassword.setBounds(195, 255, 200, 30);

        //注册的按钮
        submit.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        submit.setBounds(123, 310, 128, 47);
        submit.setBorderPainted(false);
        submit.setContentAreaFilled(false);
        submit.addMouseListener(this);

        //重置的按钮
        reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        reset.setBounds(256, 310, 128, 47);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);

        //背景图片
        JLabel background = new JLabel(new ImageIcon("image\\register\\background.png"));
        background.setBounds(0, 0, 470, 390);

        this.getContentPane().add(usernameText);
        this.getContentPane().add(passwordText);
        this.getContentPane().add(rePasswordText);
        this.getContentPane().add(username);
        this.getContentPane().add(password);
        this.getContentPane().add(rePassword);
        this.getContentPane().add(submit);
        this.getContentPane().add(reset);
        this.getContentPane().add(background);
    }

    /**
     * 初始化窗口设置，包括界面大小、标题、布局、关闭模式、居中和置顶设置。
     */
    private void initFrame() {
        //对自己的界面做一些设置。
        //设置宽高
        setSize(488, 430);
        //设置标题
        setTitle("拼图游戏 V1.0注册");
        //取消内部默认布局
        setLayout(null);
        //设置关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置居中
        setLocationRelativeTo(null);
        //设置置顶
        setAlwaysOnTop(true);
    }

    /**
     * 显示弹框的方法，用于展示指定内容。
     *
     * @param content 要显示在弹框中的文本内容
     */
    public void showDialog(String content) {
        if (!jDialog.isVisible()) {
            //把弹框中原来的文字给清空掉。
            jDialog.getContentPane().removeAll();
            JLabel jLabel = new JLabel(content);
            jLabel.setBounds(0, 0, 200, 150);
            jDialog.add(jLabel);
            //给弹框设置大小
            jDialog.setSize(200, 150);
            //要把弹框在设置为顶层 -- 置顶效果
            jDialog.setAlwaysOnTop(true);
            //要让jDialog居中
            jDialog.setLocationRelativeTo(null);
            //让弹框
            jDialog.setModal(true);
            //让jDialog显示出来
            jDialog.setVisible(true);
        }
    }
}
