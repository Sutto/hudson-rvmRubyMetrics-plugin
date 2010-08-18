package hudson.plugins.rvmRubyMetrics.railsStats;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.plugins.rvmRubyMetrics.AbstractRailsTaskPublisher;
import hudson.plugins.rvmRubyMetrics.railsStats.model.RailsStatsResults;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;

import org.codehaus.plexus.util.StringOutputStream;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Rails stats {@link Publisher}
 *
 * @author David Calavera
 *
 */
@SuppressWarnings("unchecked")
public class RailsStatsPublisher extends AbstractRailsTaskPublisher {

  @DataBoundConstructor
  public RailsStatsPublisher(String rubyString, String rakeWorkingDir) {
    super(rubyString, rakeWorkingDir, "stats");
  }

  protected void buildAction(StringOutputStream out, AbstractBuild <? , ? > build) {
    final RailsStatsParser parser = new RailsStatsParser();
    RailsStatsResults results = parser.parse(out);

    RailsStatsBuildAction action = new RailsStatsBuildAction(build, results);
    build.getActions().add(action);
  }

  @Override
  public Action getProjectAction(AbstractProject <? , ? > project) {
    return new RailsStatsProjectAction(project);
  }

  @Extension
  public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

  public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

    protected DescriptorImpl() {
      super(RailsStatsPublisher.class);
    }

    @Override
    public String getHelpFile() {
      return "/plugin/rvmRubyMetrics/railsStatsHelp.html";
    }

    @Override
    public String getDisplayName() {
      return "Publish Rails stats report";
    }

    @Override
    public boolean isApplicable(Class <? extends AbstractProject > jobType) {
      return true;
    }

  }

  @Override
  public BuildStepDescriptor<Publisher> getDescriptor() {
    return DESCRIPTOR;
  }

}
