package org.bitstrings.m2e.sourcelookup.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;

public final class SourceContainerHelper
{
    private SourceContainerHelper() {}

    public static List<IProject> getJavaProjectsAdditions( ISourceLookupDirector director )
    {
        List<IProject> mavenProjects = new ArrayList<>();

        for ( IMavenProjectFacade mavenProject : MavenPlugin.getMavenProjectRegistry().getProjects() )
        {
            try
            {
                IProjectNature nature = mavenProject.getProject().getNature( "org.eclipse.jdt.core.javanature" );

                if ( nature != null )
                {
                    mavenProjects.add( mavenProject.getProject() );
                }
            }
            catch ( CoreException e )
            {
            }
        }

        /*
        List<IJavaProject> javaProjects = new ArrayList<>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        try
        {
            for ( IJavaProject javaProject : JavaCore.create(root).getJavaProjects() )
            {
                if ( mavenProjects.contains( javaProject.getProject() ) )
                {
                    javaProjects.add( javaProject );
                }
            }
        }
        catch ( JavaModelException e )
        {
            IStatus status =
                new Status(
                    IStatus.ERROR,
                    SourceLookupPlugin.getInstance().getBundle().getSymbolicName(),
                    "Can't retrieve Java projects.",
                    e
                );

            SourceLookupPlugin.getInstance().getLog().log(status);
        }

        for ( ISourceContainer container : director.getSourceContainers() )
        {
            if ( container.getType().getId().equals( MavenSourceContainerTypeDelegate.TYPE_ID ) )
            {
                javaProjects.remove( ( (MavenSourceContainer) container ).getJavaProject() );
            }
        }

        */

        for ( ISourceContainer container : director.getSourceContainers() )
        {
            if ( container.getType().getId().equals( ProjectSourceContainer.TYPE_ID ) )
            {
                mavenProjects.remove( ( (ProjectSourceContainer) container ).getProject() );
            }
        }

        return mavenProjects;
    }

    public static ISourceContainer[] getSourceContainers( ISourceLookupDirector director )
    {
        List<IProject> additions = getJavaProjectsAdditions( director );
        ISourceContainer[] sourceContainers = new ISourceContainer[additions.size()];

        Iterator<IProject> additionsIter = getJavaProjectsAdditions( director ).iterator();

        for ( int index = 0 ; index < additions.size(); index++ )
        {
            sourceContainers[index] = new ProjectSourceContainer( additionsIter.next(), true );
        }

        return sourceContainers;
    }
}
