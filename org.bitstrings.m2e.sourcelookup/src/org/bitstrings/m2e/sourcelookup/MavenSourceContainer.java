package org.bitstrings.m2e.sourcelookup;

import static org.eclipse.jdt.launching.IRuntimeClasspathEntry.USER_CLASSES;
import static org.eclipse.m2e.jdt.IClasspathManager.CLASSPATH_RUNTIME;
import static org.eclipse.m2e.jdt.IClasspathManager.CONTAINER_ID;

import org.bitstrings.m2e.sourcelookup.internal.SourceLookupMessages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.StandardClasspathProvider;
import org.eclipse.m2e.jdt.internal.launch.MavenSourcePathProvider;
import org.eclipse.osgi.util.NLS;

public class MavenSourceContainer
    extends CompositeSourceContainer
{
    private final static StandardClasspathProvider mavenRuntimeClasspathProvider =
        new MavenSourcePathProvider()
        {
            @Override
            protected int getArtifactScope( ILaunchConfiguration configuration )
            {
                return CLASSPATH_RUNTIME;
            }
        };

    private final IJavaProject javaProject;

    public MavenSourceContainer( IJavaProject javaProject )
    {
        this.javaProject = javaProject;
    }

    @Override
    public String getName()
    {
        return NLS.bind( SourceLookupMessages.mavenSourceContainerName, getProjectName() );
    }

    @Override
    public ISourceContainerType getType()
    {
        return getSourceContainerType( MavenSourceContainerTypeDelegate.TYPE_ID );
    }

    @Override
    protected ISourceContainer[] createSourceContainers()
        throws CoreException
    {
        return fromMavenSourcePathProvider();
    }

    public IJavaProject getJavaProject()
    {
        return javaProject;
    }

    public String getProjectName()
    {
        return javaProject.getElementName();
    }

    private ISourceContainer[] fromMavenSourcePathProvider()
        throws CoreException
    {
        IRuntimeClasspathEntry mavenEntry =
            JavaRuntime.newRuntimeContainerClasspathEntry( new Path( CONTAINER_ID ), USER_CLASSES );

        ILaunchConfiguration launchConfiguration = getDirector().getLaunchConfiguration();

        ILaunchConfiguration javaProjectLaunchConfiguration =
            new JavaProjectLaunchConfiguration(
                launchConfiguration,
                null
            );

        IRuntimeClasspathEntry[] resolved =
            mavenRuntimeClasspathProvider.resolveClasspath(
                new IRuntimeClasspathEntry[]
                {
                    mavenEntry
                },
                javaProjectLaunchConfiguration
            );

        return JavaRuntime.getSourceContainers( resolved );
    }
}
