<%@ page import="java.io.OutputStream" 
%><%@ page import="java.awt.Color"
%><%@ page import="java.awt.Graphics"
%><%@ page import="java.awt.image.BufferedImage"
%><%@ page import="javax.imageio.ImageIO"
%><%

// Draw 2 bars both with a specified height and maximum width. One bar will represent value1
// the other bar will represent the difference between value1 and value2. value2 should be
// the greater of the two values.

int    width      =            Integer.parseInt(   request.getParameter( "width"      )       );
int    height     =            Integer.parseInt(   request.getParameter( "height"     )       );
double value1     =            Double.parseDouble( request.getParameter( "value1"     )       );
double value2     =            Double.parseDouble( request.getParameter( "value2"     )       );
double value3     =            Double.parseDouble( request.getParameter( "value3"     )       );
double value4     =            Double.parseDouble( request.getParameter( "value4"     )       );
Color  foreground = new Color( Integer.parseInt(   request.getParameter( "foreground" ), 16 ) );
Color  background = new Color( Integer.parseInt(   request.getParameter( "background" ), 16 ) );

int bar_length1 = (int)(value1 * (double)width);
int bar_length2 = (int)(value2 * (double)width);
int bar_length3 = (int)(value3 * (double)width);
int bar_length4 = (int)(value4 * (double)width);

BufferedImage buffer = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

Graphics g = buffer.createGraphics();

g.setColor( background );
g.fillRect( 0, 0, width, height );

int baroffset = height / 4;
int barheight = height / 4 - 1;

g.setColor( foreground );
g.fillRect( 0, baroffset * 0, bar_length1, barheight );
g.fillRect( 0, baroffset * 1, bar_length2, barheight );
g.fillRect( 0, baroffset * 2, bar_length3, barheight );
g.fillRect( 0, baroffset * 3, bar_length4, barheight );

response.setContentType( "image/png" );

OutputStream os = response.getOutputStream();

ImageIO.write( buffer, "png", os );

os.close();

%>