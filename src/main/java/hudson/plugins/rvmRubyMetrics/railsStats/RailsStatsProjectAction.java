package hudson.plugins.rvmRubyMetrics.railsStats;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.plugins.rvmRubyMetrics.AbstractRubyMetricsBuildAction;
import hudson.plugins.rvmRubyMetrics.AbstractRubyMetricsProjectAction;

public class RailsStatsProjectAction<RailsStatsBuildAction> extends AbstractRubyMetricsProjectAction {

  public RailsStatsProjectAction(AbstractProject <? , ? > project) {
    super(project);
  }

  public String getDisplayName() {
    return "Rails stats report";
  }

  public String getUrlName() {
    return "railsStats";
  }

  @Override
  protected Class getBuildActionClass() {
    return hudson.plugins.rvmRubyMetrics.railsStats.RailsStatsBuildAction.class;
  }

}
