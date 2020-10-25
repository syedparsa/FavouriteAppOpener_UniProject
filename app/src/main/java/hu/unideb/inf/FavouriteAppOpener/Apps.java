package hu.unideb.inf.FavouriteAppOpener;

public class Apps {
    private String packageName;
    private String appName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Apps(String packageName, String appName) {
        this.packageName = packageName;
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return appName;
    }

    @Override
    public boolean equals(Object o) {

        Apps apps = (Apps) o;
        return ((Apps) o).appName.equals(this.appName) && ((Apps) o).packageName.equals(this.packageName);
    }
    
}
