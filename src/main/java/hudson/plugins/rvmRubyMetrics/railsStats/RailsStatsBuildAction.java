package hudson.plugins.rvmRubyMetrics.railsStats;

import hudson.model.AbstractBuild;
import hudson.model.HealthReport;
import hudson.plugins.rvmRubyMetrics.AbstractRubyMetricsBuildAction;
import hudson.plugins.rvmRubyMetrics.railsStats.model.RailsStatsMetrics;
import hudson.plugins.rvmRubyMetrics.railsStats.model.RailsStatsResults;
import hudson.util.ChartUtil;
import hudson.util.DataSetBuilder;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;

import java.util.Map;

public class RailsStatsBuildAction extends AbstractRubyMetricsBuildAction {

  private final RailsStatsResults results;

  public RailsStatsBuildAction(AbstractBuild <? , ? > owner, RailsStatsResults results) {
    super(owner);
    this.results = results;
  }

  public RailsStatsResults getResults() {
    return results;
  }

  public String getDisplayName() {
    return "Rails stats";
  }

  public String getUrlName() {
    return "railsStats";
  }

  @Override
  protected DataSetBuilder<String, NumberOnlyBuildLabel> getDataSetBuilder() {
    DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel> dsb = new DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel>();

    for (RailsStatsBuildAction a = this; a != null; a = a.getPreviousResult()) {
      ChartUtil.NumberOnlyBuildLabel label = new ChartUtil.NumberOnlyBuildLabel(a.owner);

      Map<RailsStatsMetrics, Integer> total = a.getResults().getTotal();
      for (Map.Entry<RailsStatsMetrics, Integer> entry : total.entrySet()) {
        dsb.add(entry.getValue(), entry.getKey().prettyPrint(), label);
      }
    }

    return dsb;
  }

}
