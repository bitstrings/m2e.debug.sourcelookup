package org.bitstrings.m2e.sourcelookup.internal;

import org.eclipse.osgi.util.NLS;

public final class SourceLookupMessages {

  public static String mavenSourceContainerName;

  public static String MyMvnSourceContainerBrowser_Dialog_Title;

  public static String myMvnSourceContainerTypeDelegate_ContainerIsMissing;

  public static String MyMvnSourceContainerTypeDelegate_InvalidFormat;

  public static String MyMvnSourceContainerTypeDelegate_ProjectNameIsMissing;

  private static final String BUNDLE_NAME = "org.bitstrings.m2e.sourcelookup.internal.SourceLookupMessages";

  static {
    NLS.initializeMessages(BUNDLE_NAME, SourceLookupMessages.class);
  }

  private SourceLookupMessages() {
    // prevent instantiation
    throw new UnsupportedOperationException("invocation of sealed constructor");
  }

}
