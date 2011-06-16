<%@ page  pageEncoding = "UTF-8" contentType="image/jpeg" import = "java.awt.geom.AffineTransform,java.io.*,javax.imageio.*,java.util.*,java.awt.image.*,java.awt.*" %>
<%
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-Type", "image/jpeg");
        //图片宽高
        int width = 95;
        int height = 38;
        BufferedImage bimage = null;
        Graphics2D g = null;

        String value;
        try
        {


            bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        
            Random rand=new Random(System.currentTimeMillis());
            g = bimage.createGraphics();

            // 设置随机背景色
            Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            //Color color = new Color(7, 147,77);

            // 填充深色背景
            g.setColor(color.darker());
            g.fillRect(0, 0, width, height);
            
            // 设置字体
            g.setFont(new Font("arial",Font.BOLD,36));
            
            // 随机生成字符,根据截取的位数决定产生的数字
            value = UUID.randomUUID().toString().replace("-","").substring(0,4);

            int w = (g.getFontMetrics()).stringWidth(value);
            int d = (g.getFontMetrics()).getDescent();
            int a = (g.getFontMetrics()).getMaxAscent();
            
            int x = 0, y =0;
            
            // 设置随机线条,10这个数值越大图片中线条越稀蔬
            for (int i = 0; i < height; i += 10)
            {
               g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
               g.drawLine(x, y + i, width, y+i);
            }

            // reset x and y
            x=0;
            y=0;
            
            //设置随机线条,10这个数值越大图片中线条越稀蔬
            for (int i = 0; i < height; i += 10)
            {
               g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
               g.drawLine(x, y + d - i, width + w, height + d - i);
            }
            
            //展示验证码中颜色,随机
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)).brighter());
            
            // 设置文字出现位置为中央
            x = width/2 - w/2;
            y = height/2 + a/2 - 2;
            
            //文字变形设置
            AffineTransform fontAT = new AffineTransform();
            int xp = x-2;
            // 每个文字都变形
            for (int c=0;c<value.length();c++)
            {
                // 产生弧度
                int rotate = rand.nextInt(20);
                fontAT.rotate(rand.nextBoolean() ? Math.toRadians(rotate) : -Math.toRadians(rotate/2));
                Font fx = new Font("arial", Font.BOLD, 36).deriveFont(fontAT);
                g.setFont(fx);
                String ch = String.valueOf(value.charAt(c));
                int ht = rand.nextInt(3);
                // 打印字并移动位置
                g.drawString(ch, xp, y + (rand.nextBoolean()?-ht:ht));
                //移动指针.
                xp+=g.getFontMetrics().stringWidth(ch) + 2;
            }
            // 打印出图片
            ImageIO.write(bimage, "png",response.getOutputStream());
            out.clear();
            out=pageContext.pushBody(); 
            //设置进当前会话
            session.setAttribute("ss", value);

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (g != null)
            {
                g.dispose();
            }
        }
%>