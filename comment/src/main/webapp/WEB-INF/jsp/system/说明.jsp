<%--  %@：  指令
page language="java"....作用：
第一阶段：pageEncoding将jsp编译成Servlet（.java）文件。-->
<!-- 第二阶段：从Servlet文件（.java）到Java字节码文件（.class），从UTF-8到UTF-8。在这一阶段中，不论JSP编写时候用的是什么编码方案，
经过这个阶段的结果全部是UTF-8的encoding的java源码。JAVAC用UTF-8的encoding读取java源码，编译成UTF-8编码的二进制码（即.class），
这是JVM对常数字串在二进制码（java encoding）内表达的规范。这一过程是由JVM的内在规范决定的，不受外界控制。-->
<!-- 第三阶段：从服务器到浏览器，这在一过程中用到的指令是contentType。
输出过程中，由contentType属性中的charset来指定，将UTF8形式的二进制码以charset的编码形式来输出。
如果没有人为设定，则默认的是ISO-8859-1的形式。 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--  声明了文档的根元素是 html，它在公共标识符被定义为 "-//W3C//DTD XHTML 1.0 Transitional//EN" 的 DTD 中进行了定义。
浏览器将明白如何寻找匹配此公共标识符的 DTD。如果找不到，浏览器将使用公共标识符后面的 URL 作为寻找 DTD 的位置。
另外，需要知道的是，HTML 4.01 规定的三种文档类型、XHTML 1.0 规定的三种 XML 文档类型都是：Strict、Transitional 以及 Frameset。
而这句<html xmlns="http://www.w3.org/1999/xhtml">，是在文档中的<html> 标签中使用 xmlns 属性，以指定整个文档所使用的主要命名空间。 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>



</body>
</html>
