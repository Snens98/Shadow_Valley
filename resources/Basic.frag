#version 430 core
#extension GL_ARB_explicit_uniform_location : enable


// Algorithm based on algorithmen from
// Sellers, Graham, Wright, Richard S., Haemel, Nicholas (2014).
// OpenGL Super Bible. 6th edition. Addison Wesley.

// Point light source
// To use this shader set for generating a directional light source,
// put the light source very far away from the objects to be lit

// Puts a texture on the surfaces of the object

// Author: Karsten Lehn
// Version: 12.11.2017, 16.9.2019


layout (location = 7) uniform vec4 materialEmission;
layout (location = 8) uniform vec4 materialAmbient;
layout (location = 9) uniform vec4 materialDiffuse;
layout (location = 10) uniform vec4 materialSpecular;
layout (location = 11) uniform float materialShininess;

layout (location = 3) uniform vec4 lightdir;
layout (location = 4) uniform vec4 lightambient;
layout (location = 5) uniform vec4 lightdiffuse;
layout (location = 6) uniform vec4 lightspecular;

layout (binding = 0) uniform sampler2D tex;
layout (location = 12) uniform float texRepeat = 1.0;


in vec3 v_normal;
in vec3 color;
in vec3 position;
in mat4 v_Matrix;
in mat4 m_Matrix;
in vec2 UV;


out vec4 FragColor;
vec4 lightdirection;
vec3 normal;


void main(void)
{
    normal = mat3(v_Matrix * m_Matrix) * v_normal;

    vec3 emissiv = vec3(materialEmission);
    vec3 ambient = vec3(materialAmbient) * vec3(lightambient);
    vec3 diffuseAlbedo = vec3(materialDiffuse) * vec3(lightdiffuse);
    vec3 specularAlbedo = vec3(materialSpecular) * vec3(lightspecular);

    lightdirection = transpose(inverse(v_Matrix)) * lightdir;

    vec3 view = normalize(-position);
    vec3 light = normalize(vec3(-lightdirection));
    vec3 nNormal = normalize(normal);
    vec3 reflection = reflect(-light, nNormal);

    vec3 diffuse = max(dot(nNormal, light), 0.0) * diffuseAlbedo;




//    vec3 lightDir   = normalize(vec3(10,  500000, 500) - position);
//    vec3 viewDir    = normalize(-position.xyz - position);
//    vec3 halfwayDir = normalize(lightDir + viewDir);
//    float spec = pow(max(dot(normal, halfwayDir), 0.0), materialShininess);
//    vec3 specular = specularAlbedo * spec;


    vec3 specular = pow(max(dot(reflection, view), 0.0), materialShininess) * specularAlbedo;

//    FragColor = (vec4(emissiv + ambient + diffuse, 1.0)) + vec4(specular, 1.0);
         FragColor = (vec4(emissiv + ambient + diffuse, 1.0) * texture(tex, UV*texRepeat)) + vec4(specular, 1.0);
}

