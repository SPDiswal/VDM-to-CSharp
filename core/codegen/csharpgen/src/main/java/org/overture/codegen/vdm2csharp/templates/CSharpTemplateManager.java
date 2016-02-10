package org.overture.codegen.vdm2csharp.templates;

import org.overture.codegen.merging.TemplateManager;

/**
 * The C# template manager defines the location of the C#-related Velocity templates.
 */
public final class CSharpTemplateManager extends TemplateManager
{
    /**
     * @param root the root folder of the templates.
     */
    public CSharpTemplateManager(final String root)
    {
        super(root);
    }
}
