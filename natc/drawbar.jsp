<%@ page import="java.io.OutputStream" 
%><%@ page import="java.awt.Color"
%><%@ page import="java.awt.Graphics"
%><%@ page import="java.awt.image.BufferedImage"
%><%@ page import="javax.imageio.ImageIO"
%><%

// Draw a single bar with a specified height and maximum width to the width of the specified value

int    width      =            Integer.parseInt(   request.getParameter( "width"      )       );
int    height     =            Integer.parseInt(   request.getParameter( "height"     )       );
double value      =            Double.parseDouble( request.getParameter( "value"      )       );
Color  foreground = new Color( Integer.parseInt(   request.getParameter( "foreground" ), 16 ) );
Color  background = new Color( Integer.parseInt(   request.getParameter( "background" ), 16 ) );

int bar_length = (int)(value * (double)width);

BufferedImage buffer = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

Graphics g = buffer.createGraphics();

g.setColor( background );
g.fillRect( 0, 0, width, height );

g.setColor( foreground );
g.fillRect( 0, 0, bar_length, height );

response.setContentType( "image/png" );

OutputStream os = response.getOutputStream();

ImageIO.write( buffer, "png", os );

os.close();

%>