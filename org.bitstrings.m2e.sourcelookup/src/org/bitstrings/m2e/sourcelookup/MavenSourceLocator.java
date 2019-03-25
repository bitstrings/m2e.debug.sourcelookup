package org.bitstrings.m2e.sourcelookup;

import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaSourceLookupParticipant;

public class MavenSourceLocator
    extends org.eclipse.m2e.internal.launch.MavenSourceLocator
{
    @Override
    public void initializeParticipants()
    {
        super.initializeParticipants();

        for ( ISourceLookupParticipant participant : getParticipants() )
        {
            if ( participant instanceof JavaSourceLookupParticipant )
            {
                return;
            }
        }

        addParticipants( new ISourceLookupParticipant[] { new JavaSourceLookupParticipant() } );
    }
}
