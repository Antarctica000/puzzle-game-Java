package UI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//游戏的主界面，包括和游戏相关的所有逻辑
public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //创建选项的条目对象
    JMenuItem people = new JMenuItem("人物");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem replayItem = new JMenuItem("重新游戏");
    /*JMenuItem reLoginItem = new JMenuItem("重新登录");*/
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("作者微信");
    //定义一个变量用来展示当前图片的路径
    String path = "image\\rtwo\\rtwo-";
    //很多图片路径的数组
    String[] peoplePath = {
            "image\\rtwo\\rtwo-",
            "image\\rone\\rone-",
            "image\\Eron\\Eron-",
            "image\\levi\\levi-"
    };
    String[] animalPath = {
            "image\\cat\\cat-",
            "image\\cutty\\cutty-"
    };
    //创建二维数组记录顺序
    int[][] data = new int[3][3];
    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;
    //定义一个存储正确数据的二维数组(判断胜利)
    int[][] win = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };
    //定义统计步数的变量
    int step = 0;

    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据(打乱顺序)
        initData();
        //初始化图片(打乱之后)
        initImage();
        //让界面显示
        this.setVisible(true);
    }

    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603, 680);
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认的居中模式
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    private void initJMenuBar() {
        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建菜单上面两个选项
        JMenu fonctionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于作者");
        //创建更换图片
        JMenu changeImage = new JMenu("更换图片");
        //将每个选项下的条目添加到选项中
        fonctionJMenu.add(changeImage);
        changeImage.add(people);
        changeImage.add(animal);
        fonctionJMenu.add(replayItem);
        /*fonctionJMenu.add(reLoginItem);*/
        fonctionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);
        //给条目绑定事件
        people.addActionListener(this);
        animal.addActionListener(this);
        replayItem.addActionListener(this);
        /*reLoginItem.addActionListener(this);*/
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        //将选项添加进菜单中
        jMenuBar.add(fonctionJMenu);
        jMenuBar.add(aboutJMenu);
        //给界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initData() {
        Random r = new Random();
        int[] tem = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int i = 0; i < tem.length; i++) {
            int x = r.nextInt(tem.length);
            int temp = tem[i];
            tem[i] = tem[x];
            tem[x] = temp;
        }
        for (int i = 0; i < tem.length; i++) {
            //记录0索引的位置
            if (tem[i] == 0) {
                x = i / 3;
                y = i % 3;
            }
            data[i / 3][i % 3] = tem[i];
        }
    }

    private void initImage() {
        //清空原本出现的所有图片
        this.getContentPane().removeAll();
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("image\\else\\win.png"));
            winJLabel.setBounds(0, 0, 600, 650);
            this.getContentPane().add(winJLabel);
        }
        JLabel stepCount = new JLabel("步数:" + step);
        stepCount.setBounds(0, 0, 100, 20);
        this.getContentPane().add(stepCount);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //获取当前加载图片的序号
                int num = data[i][j];
                //创建一个JLabel的对象
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));
                //指定图片位置
                jLabel.setBounds(200 * j, 216 * i, 200, 216);
                //给图片添加边框(优化)
                jLabel.setBorder(new BevelBorder(0));
                //把管理容器添加到界面中!=this.add(jLabel);
                this.getContentPane().add(jLabel);
            }
        }
        //刷新界面
        this.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            //将界面中的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(0, 0, 600, 650);
            this.getContentPane().add(all);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //若游戏胜利，则不需要再移动了
        if (victory())
            return;
        //对上下左右判断(左37上38右39下40)
        int code = e.getKeyCode();
        switch (code) {
            case 37:
                if (y == 2)
                    return;
                data[x][y] = data[x][y + 1];
                data[x][y + 1] = 0;
                y++;
                step++;
                initImage();
                break;
            case 38:
                if (x == 2)
                    return;
                data[x][y] = data[x + 1][y];
                data[x + 1][y] = 0;
                x++;
                step++;
                initImage();
                break;
            case 39:
                if (y == 0)
                    return;
                data[x][y] = data[x][y - 1];
                data[x][y - 1] = 0;
                y--;
                step++;
                initImage();
                break;
            case 40:
                if (x == 0)
                    return;
                data[x][y] = data[x - 1][y];
                data[x - 1][y] = 0;
                x--;
                step++;
                initImage();
                break;
            case 65:
                initImage();
            case 87:
                data = new int[][]{
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 0}
                };
                initImage();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj = e.getSource();
        Random r = new Random();
        if (obj == replayItem) {
            System.out.println("重新游戏");
            //再次打乱顺序
            initData();
            //计步器清零(先清零再加载)
            step = 0;
            //重新加载图片(小bug解决)
            initImage();
        } /*else if (obj == reLoginItem) {
            System.out.println("重新登录");
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        }*/ else if (obj == closeItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("作者微信");
            //创建一个弹窗对象
            JDialog jDialog = new JDialog();
            //创建一个管理图片的容器对象
            JLabel jLabel = new JLabel(new ImageIcon("image\\else\\wechat.png"));
            jLabel.setBounds(0, 0, 249, 289);
            jDialog.getContentPane().add(jLabel);
            //弹框设置大小且置顶居中
            jDialog.setSize(344, 344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭则无法下面的操作
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj == people) {
            int x = r.nextInt(4);
            path=peoplePath[x];
            initData();
            step = 0;
            initImage();
        }
        else if (obj == animal) {
            int x = r.nextInt(2);
            path=animalPath[x];
            initData();
            step = 0;
            initImage();
        }
    }

    //判断是否胜利
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j])
                    return false;
            }
        }
        return true;
    }
}
