package il.org.spartan.refactoring.preferences;

import il.org.spartan.refactoring.builder.*;

@SuppressWarnings("javadoc") public class PluginPreferencesResources {
  /** Page description **/
  public static final String PAGE_DESCRIPTION = "Preferences for the Spartan Refactoring plug-in";
  /** General preferences **/
  public static final String PLUGIN_STARTUP_BEHAVIOR_ID = "pref_startup_behavior";
  public static final String PLUGIN_STARTUP_BEHAVIOR_TEXT = "Plugin startup behavior:";
  public static final String[][] PLUGIN_STARTUP_BEHAVIOR_OPTIONS = { { "Remember individual project settings", "remember" },
    { "Enable for all projects", "always_on" }, { "Disable for all projects", "always_off" } };
  public static final String NEW_PROJECTS_ENABLE_BY_DEFAULT_ID = "pref_enable_by_default_for_new_projects";
  public static final String NEW_PROJECTS_ENABLE_BY_DEFAULT_TEXT = "Enable by default for newly created projects";
  public static final String ENABLE_BINDING_RESOLUTION_ID = "pref_resolve_bindings";
  public static final String ENABLE_BINDING_RESOLUTION_TEXT = "Resolve bindings";
  /** Enabled wrings **/
  public static final String CONSOLIDATE_ASSIGNMENTS_STATEMENTS_ID = "pref_wrings_consolidate_assignments_statements";
  public static final String CONSOLIDATE_ASSIGNMENTS_STATEMENTS_TEXT = "Consolidate assignments and repeating statements";
  public static final String SIMPLIFY_NESTED_BLOCKS_ID = "pref_wrings_simplify_nested_blocks";
  public static final String SIMPLIFY_NESTED_BLOCKS_TEXT = "Simplify nested code blocks";
  public static final String ELIMINATE_TEMP_ID = "pref_wrings_eliminate_temp";
  public static final String ELIMINATE_TEMP_TEXT = "Eliminate temporary variables";
  public static final String REMOVE_REDUNDANT_PUNCTUATION_ID = "pref_wrings_remove_redundant_punctuation";
  public static final String REMOVE_REDUNDANT_PUNCTUATION_TEXT = "Remove redundant curly braces and semicolons";
  public static final String IF_TO_TERNARY_ID = "pref_wrings_if_to_ternary";
  public static final String IF_TO_TERNARY_TEXT = "Convert if statements to ternary operators";
  public static final String REFACTOR_INEFFECTIVE_ID = "pref_wrings_refactor_ineffective";
  public static final String REFACTOR_INEFFECTIVE_TEXT = "Refactor empty or ineffective code";
  public static final String REORDER_EXPRESSIONS_ID = "pref_wrings_reorder_expressions";
  public static final String REORDER_EXPRESSIONS_TEXT = "Reorder expressions for better readability";
  public static final String OPTIMIZE_ANNOTATIONS_ID = "pref_wrings_optimize_annotations";
  public static final String OPTIMIZE_ANNOTATIONS_TEXT = "Optimize annotations";
  public static final String RENAME_PARAMETERS_ID = "pref_wrings_rename_parameters";
  public static final String RENAME_PARAMETERS_TEXT = "Rename method parameters";
  public static final String RENAME_RETURN_VARIABLE_ID = "pref_wrings_rename_return_variable";
  public static final String RENAME_RETURN_VARIABLE_TEXT = "Rename return variable to $";
  public static final String REPLACE_CLASS_INSTANCE_CREATION_ID = "pref_wrings_replace_class_instance_creation";
  public static final String REPLACE_CLASS_INSTANCE_CREATION_TEXT = "Replace class instance creation with recommended form";
  public static final String DISCARD_METHOD_INVOCATION_ID = "pref_wrings_discard_method_invocation";
  public static final String DISCARD_METHOD_INVOCATION_TEXT = "Replace method invocation with proper replacement";
  public static final String REPLACE_SEQUENSER_ID = "pref_wrings_replace_sequencer";
  public static final String REPLACE_SEQUENSER_TEXT = "Replace a sequencer with proper replacement";
  public static final String SWITCH_IF_CONVERTION_ID = "pref_wrings_switch_if_convertion";
  public static final String SWITCH_IF_CONVERTION_TEXT = "Convertion between switch and if statements and vise versa";
  public static final String WRING_COMBO_OPTIONS[][] = { { "Enabled", "on" }, { "Disabled", "off" } };

  //
  public static final boolean getResolveBindingEnabled() {
    return Plugin.getDefault() != null && Plugin.getDefault().getPreferenceStore().getBoolean(ENABLE_BINDING_RESOLUTION_ID);
  }

  /**
   * An enum holding together all the "enabled spartanizations" options, also
   * allowing to get the set preference value for each of them
   */
  public enum WringGroup {
    CONSOLIDATE_ASSIGNMENTS_STATEMENTS( //
        CONSOLIDATE_ASSIGNMENTS_STATEMENTS_ID, //
        CONSOLIDATE_ASSIGNMENTS_STATEMENTS_TEXT), //
        SIMPLIFY_NESTED_BLOCKS( //
            SIMPLIFY_NESTED_BLOCKS_ID, //
            SIMPLIFY_NESTED_BLOCKS_TEXT), //
            ELIMINATE_TEMP( //
                ELIMINATE_TEMP_ID, //
                ELIMINATE_TEMP_TEXT), //
                DISCARD_METHOD_INVOCATION( //
                    DISCARD_METHOD_INVOCATION_ID, //
                    DISCARD_METHOD_INVOCATION_TEXT), //
                    REMOVE_REDUNDANT_PUNCTUATION( //
                        REMOVE_REDUNDANT_PUNCTUATION_ID, //
                        REMOVE_REDUNDANT_PUNCTUATION_TEXT), //
                        Ternarize( //
                            IF_TO_TERNARY_ID, //
                            IF_TO_TERNARY_TEXT), //
                            REFACTOR_INEFFECTIVE( //
                                REFACTOR_INEFFECTIVE_ID, //
                                REFACTOR_INEFFECTIVE_TEXT), //
                                REORDER_EXPRESSIONS( //
                                    REORDER_EXPRESSIONS_ID, //
                                    REORDER_EXPRESSIONS_TEXT), //
                                    OPTIMIZE_ANNOTATIONS( //
                                        OPTIMIZE_ANNOTATIONS_ID, //
                                        OPTIMIZE_ANNOTATIONS_TEXT), //
                                        RENAME_PARAMETERS( //
                                            RENAME_PARAMETERS_ID, //
                                            RENAME_PARAMETERS_TEXT), //
                                            RENAME_RETURN_VARIABLE( //
                                                RENAME_RETURN_VARIABLE_ID, //
                                                RENAME_RETURN_VARIABLE_TEXT), //
                                                REPLACE_CLASS_INSTANCE_CREATION( //
                                                    REPLACE_CLASS_INSTANCE_CREATION_ID, //
                                                    REPLACE_CLASS_INSTANCE_CREATION_TEXT), //
                                                    REPLACE_SEQUENSER( //
                                                        REPLACE_SEQUENSER_ID, //
                                                        REPLACE_SEQUENSER_TEXT), //
                                                        SWITCH_IF_CONVERTION( //
                                                            SWITCH_IF_CONVERTION_ID, //
                                                            SWITCH_IF_CONVERTION_TEXT), //
                                                            ;
    private final String id, label;

    WringGroup(final String id, final String label) {
      this.id = id;
      this.label = label;
    }
    public String getId() {
      return id;
    }
    public String getLabel() {
      return label;
    }
    /**
     * Queries the plugin's preferences store and retrieves whether the wring
     * group is currently enabled by the user
     *
     * @return whether the group is enabled or not
     */
    public boolean isEnabled() {
      return Plugin.getDefault() == null || "on".equals(Plugin.getDefault().getPreferenceStore().getString(id));
    }
  }
}
