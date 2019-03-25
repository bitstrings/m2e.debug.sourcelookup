package org.bitstrings.m2e.sourcelookup;

import java.util.Collections;
import java.util.LinkedList;

import org.bitstrings.m2e.sourcelookup.internal.SourceContainerHelper;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.jdt.internal.launching.sourcelookup.advanced.AdvancedRemoteJavaLaunchDelegate;
import org.eclipse.jdt.internal.launching.sourcelookup.advanced.AdvancedSourceLookupDirector;

public class MavenAwareRemoteJavaLaunchDelegate
    extends AdvancedRemoteJavaLaunchDelegate
{
    public MavenAwareRemoteJavaLaunchDelegate()
    {
        super();
    }

    @Override
    public ILaunch getLaunch( ILaunchConfiguration configuration, String mode )
        throws CoreException
    {
        ILaunch launch = super.getLaunch( configuration, mode );

        ISourceLocator locator = launch.getSourceLocator();

        if ( locator instanceof AdvancedSourceLookupDirector )
        {
            AdvancedSourceLookupDirector director = (AdvancedSourceLookupDirector) locator;

            LinkedList<ISourceContainer> containers = new LinkedList<>();
            for ( ISourceContainer container : SourceContainerHelper.getSourceContainers( director ) )
            {
                containers.add( container );
            }
            Collections.addAll( containers, director.getSourceContainers() );

            director.setSourceContainers( containers.toArray( new ISourceContainer[containers.size()] ) );
        }

        return launch;
    }
}
