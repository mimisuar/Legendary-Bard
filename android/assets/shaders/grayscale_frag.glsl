#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()
{
    vec4 tex_color = texture2D(u_texture, v_texCoords);
    float avg = (tex_color.r + tex_color.g + tex_color.b) / 3.0;
    gl_FragColor = vec4(avg, avg, avg, tex_color.a);
}