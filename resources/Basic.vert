#version 430 core
#extension GL_ARB_explicit_uniform_location : enable

// Algorithm based on algorithm from
// Sellers, Graham, Wright, Richard S., Haemel, Nicholas (2014).
// OpenGL Super Bible. 6th edition. Addison Wesley.

// Point light source
// To use this shader set for generating a directional light source,
// put the light source very far away from the objects to be lit

// Puts a texture on the surfaces of the object

// Author: Karsten Lehn
// Version: 12.11.2017, 16.9.2019

// position, color, normal and texture coordinates of vertex as input vertex attribute
layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vInUV;
layout (location = 2) in vec3 vNormal;
//layout (location = 3) in vec2 vInUV;

layout (location = 0) uniform mat4 mMatrix;
layout (location = 1) uniform mat4 vMatrix;
layout (location = 2) uniform mat4 pMatrix;


out vec3 color;
out vec3 v_normal;
out vec3 position;
out mat4 v_Matrix;
out mat4 m_Matrix;
out vec2 UV;

void main(void)
{
    UV = vInUV;
    v_Matrix = vMatrix;
    m_Matrix = mMatrix;

//    v_normal = mat3(vMatrix * mMatrix) * vNormal;
    v_normal = vNormal;

    position = vec3(vMatrix * mMatrix * vec4(vPosition, 1.0));

    //    color = vInColor;
    gl_Position =  pMatrix*vMatrix*mMatrix*vec4(vPosition, 1.0);
}


