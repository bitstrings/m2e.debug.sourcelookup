package org.bitstrings.m2e.sourcelookup;

import org.bitstrings.m2e.sourcelookup.internal.SourceLookupMessages;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.AbstractSourceContainerTypeDelegate;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MavenSourceContainerTypeDelegate
    extends AbstractSourceContainerTypeDelegate
{
    public static final String TYPE_ID = "org.bitstrings.m2e.sourcelookup.MavenSourceContainerType";

    private static final String MVNCONTAINER = "mvncontainer";

    private static final String PROJECT_NAME = "projectName";

    @Override
    public ISourceContainer createSourceContainer( String memento )
        throws CoreException
    {
        System.out.println( "MavenSourceContainerTypeDelegate : createSourceContainer = " + memento );

        Node node = parseDocument( memento );

        if ( node.getNodeType() == Node.ELEMENT_NODE )
        {
            Element element = (Element) node;

            if ( MVNCONTAINER.equals( element.getNodeName() ) )
            {
                String projectName = element.getAttribute( PROJECT_NAME );
                if ( ( projectName == null ) || projectName.isEmpty() )
                {
                    abort( SourceLookupMessages.MyMvnSourceContainerTypeDelegate_ProjectNameIsMissing, null );
                }

                return
                    new ProjectSourceContainer(
                        ResourcesPlugin.getWorkspace().getRoot().getProject( projectName ), true
                    );
            }

            abort( SourceLookupMessages.myMvnSourceContainerTypeDelegate_ContainerIsMissing, null );
        }

        abort( SourceLookupMessages.MyMvnSourceContainerTypeDelegate_InvalidFormat, null );

        return null;
    }

    @Override
    public String getMemento( ISourceContainer container )
        throws CoreException
    {
        ProjectSourceContainer sourceContainer = (ProjectSourceContainer) container;
        Document document = newDocument();
        Element element = document.createElement( MVNCONTAINER );

        element.setAttribute( PROJECT_NAME, sourceContainer.getName() );
        document.appendChild( element );

        return serializeDocument( document );
    }
}
