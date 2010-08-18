package hudson.plugins.rvmRubyMetrics;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.StreamBuildListener;
import hudson.plugins.rvmRake.Rake;

import java.io.IOException;

import org.codehaus.plexus.util.StringOutputStream;

public abstract class AbstractRailsTaskPublisher extends AbstractRubyMetricsPublisher {

  protected final Rake rake;
  protected final String rubyString;
  protected final String rakeWorkingDir;
  private final String task;

  protected AbstractRailsTaskPublisher(String rubyString, String rakeWorkingDir, String task) {
    this.rubyString = rubyString;
    this.rakeWorkingDir = rakeWorkingDir;
    this.task = task;
    this.rake = new Rake(this.rubyString, null, task, null, this.rakeWorkingDir, true);
  }

  public String getRubyString() {
    return rubyString;
  }

  public String getRakeWorkingDir() {
    return rakeWorkingDir;
  }

  private boolean isRailsProject(FilePath workspace) {
    try { //relaxed rails app schema
      return workspace != null && workspace.isDirectory()
             && workspace.list("app") != null && workspace.list("config") != null
             && workspace.list("db") != null && workspace.list("test") != null;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public boolean perform(AbstractBuild <? , ? > build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
    FilePath workspace = build.getModuleRoot();

    if (!isRailsProject(workspace)) {
      String message = "Your workspace is not a valid rails application directory";
      if (workspace != null) {
        message += ": " + workspace.getName();
      }
      return fail(build, listener, message);
    }

    listener.getLogger().println("Publishing rails " + task + " report...");

    StringOutputStream out = new StringOutputStream();
    BuildListener stringListener = new StreamBuildListener(out);

    if (rake.perform(build, launcher, stringListener)) {
      buildAction(out, build);
    }

    return true;
  }

  protected abstract void buildAction(StringOutputStream out, AbstractBuild <? , ? > build);
}
