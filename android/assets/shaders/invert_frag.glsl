#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main()
{
    vec4 tex_color = texture2D(u_texture, v_texCoords);
    gl_FragColor = vec4(1, 1, 1, 1) - tex_color;
    gl_FragColor.a = tex_color.a;
}