package org.bitstrings.m2e.sourcelookup.internal;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class SourceLookupPlugin
    extends Plugin
{
    private static SourceLookupPlugin plugin;

    public static SourceLookupPlugin getInstance()
    {
        return plugin;
    }

    @Override
    public void start(final BundleContext bundleContext)
        throws Exception
    {
        super.start(bundleContext);
        plugin = this;
    }

    @Override
    public void stop(final BundleContext bundleContext)
        throws Exception
    {
        plugin = null;
        super.stop(bundleContext);
    }
}
