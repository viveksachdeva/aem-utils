import groovy.transform.Field
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

def cli = new CliBuilder()
cli.with {
    h(longOpt: 'help', 'Help - Usage Information')
    f(longOpt: 'fileReference', 'Path of XML file', type: String, args: 1)
    b(longOpt: 'baseContentPath', 'Root for content', type: String, required:true, args: 1)
    h(longOpt: 'host', 'Host Name(Using localhost by default)', type: String, args: 1)
    p(longOpt: 'port', 'Port(Using 4502 by default)', type: String, args: 1)
}

def opt = cli.parse(args)
if (!opt) return
if (opt.h) cli.usage()

def baseContentPath = opt.b

String filePath = opt.f?:"blogs.xml"
String hostName = opt.h?:"localhost"
String portNumber = opt.p?:"4502"
MigrationConfiguration.client = new HTTPBuilder("http://${hostName}:${portNumber}" as String)

@Field String resourceType = "myproj/components/resource"


println ":::Using Following configuration:::"
println "       File              ::: ${filePath}"
println "       Server            ::: http:://${hostName}:${portNumber}"
println "Root for posting content :::: ${baseContentPath}"
println "::::::::::::::::::::::::::::::::::::"


def file = new File(filePath)
if(!file.exists()){
    println "Incorrect file path ${filePath}... Exiting"
    return
}


void callPost(String baseURL, Map contentMap) {
    /*Setting auth basic in request doesnt work... Had to set it in headers*/
//        http.auth.basic("admin", "admin")
    MigrationConfiguration.client.request(Method.POST) {
        uri.path = baseURL
        requestContentType = ContentType.URLENC
        headers.'Authorization' = "Basic ${"admin:admin".bytes.encodeBase64().toString()}"
        body = contentMap

        response.success = { resp ->
        }

        response.failure = { resp -> println "\nERROR: ${resp.statusLine} for ${uri.path}" }
    }
}


def timeStart = System.currentTimeMillis()
println "Starting XML Parsing at ::: ${timeStart}"
/*Using Groovy XML Parser to parse XML*/
def records = new XmlParser().parseText(file.text)?.blog

/*Iterate over all XML records and create a map that will be POSTed to AEM*/
records.eachWithIndex { blog, idx ->
    def name = blog.name.text()
    def content = blog.outline.text()
    def status = blog.status.text()
    def parentSubject = blog.subject_parent.text()
    Map contentMap = [
            "./jcr:primaryType": "cq:Page",
            "./jcr:content/jcr:primaryType": "cq:PageContent",
            "./jcr:content/jcr:title": "${name}",
            "./jcr:content/blog/sling:resourceType": resourceType,
            "./jcr:content/blog/status": status,
            "./jcr:content/blog/parentSubject": parentSubject,
            "./jcr:content/blog/text/sling:resourceType": "foundation/components/text",
            "./jcr:content/blog/text/text": "${content}"
    ]
    callPost("${baseContentPath}blog${idx}", contentMap)
}

println "Done with posting ${records.size()} requests at : ${System.currentTimeMillis()}"

println "Completed in ${(System.currentTimeMillis() - timeStart)/1000} sec"


class MigrationConfiguration{
    static HTTPBuilder client
}