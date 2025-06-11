package ir.sinatech.rial_digital_service_bus.model;

public class ProcessModel {
    public String id;
    public String key;
    public String name;
    public int version;
    public String deploymentId;
    public String resourceName;
    public String diagramResourceName;
    public String startFormResourceKey;
    public boolean graphicNotationDefined;

    // Optional: to help with debugging
    @Override
    public String toString() {
        return "ProcessModel{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", deploymentId='" + deploymentId + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", diagramResourceName='" + diagramResourceName + '\'' +
                ", startFormResourceKey='" + startFormResourceKey + '\'' +
                ", graphicNotationDefined=" + graphicNotationDefined +
                '}';
    }
}