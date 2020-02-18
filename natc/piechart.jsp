<%@ page import="java.io.OutputStream" 
%><%@ page import="java.awt.Color"
%><%@ page import="java.awt.Graphics"
%><%@ page import="java.awt.image.BufferedImage"
%><%@ page import="javax.imageio.ImageIO"
%><%
String[] slices = request.getParameterValues("slice");
String[] colors = request.getParameterValues("color");

int[]   sizes = new int[slices.length];
Color[] cols  = new Color[slices.length];

for( int i = 0; i < slices.length; i++ ) {
	
	sizes[i] =            Integer.parseInt( slices[i]      );
	cols[i]  = new Color( Integer.parseInt( colors[i], 16) );
}

int   width      =            Integer.parseInt( request.getParameter( "width"      )       );
int   height     =            Integer.parseInt( request.getParameter( "height"     )       );
Color background = new Color( Integer.parseInt( request.getParameter( "background" ), 16 ) );

BufferedImage buffer = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

Graphics g = buffer.createGraphics();

g.setColor( background );
g.fillRect( 0, 0, width, height );

int arc = 0;

for( int i = 0; i < sizes.length; i++ ) {
	
    g.setColor( cols[i] );
    
    g.fillArc( 0, 0, width, height, arc, sizes[i] );
    
    arc += sizes[i];
}

response.setContentType( "image/png" );

OutputStream os = response.getOutputStream();

ImageIO.write( buffer, "png", os );

os.close();
%>